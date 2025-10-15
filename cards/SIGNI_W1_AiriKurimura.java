package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_AiriKurimura extends Card {

    public SIGNI_W1_AiriKurimura()
    {
        setImageSets("WX25-CP1-050");

        setOriginalName("栗村アイリ");
        setAltNames("クリムラアイリ Kirimura Airi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜ブルアカ＞のシグニがある場合、次の対戦相手のターンの間、対戦相手は%Xを支払わないかぎり、中央のシグニゾーンにあるシグニでアタックできない。" +
                "~{{C $TP：[[シャドウ（レベル２以下）]]"
        );

        setName("en", "Kurimura Airi");

        setName("en_fan", "Airi Kurimura");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Blue Archive>> SIGNI on your field, during your opponent's next turn, your opponent can't attack with SIGNI in their center SIGNI zone unless they pay %X." +
                "~{{C $TP: [[Shadow (level 2 or lower)]]."
        );

		setName("zh_simplified", "栗村爱理");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上有其他的<<ブルアカ>>精灵的场合，下一个对战对手的回合期间，对战对手如果不把%X:支付，那么中央的精灵区的精灵不能攻击。\n" +
                "~{{C$TP :[[暗影（等级2以下）]]@@\n" +
                "（$TP :的能力，只有在对战对手的回合期间变为有效）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().fromLocation(CardLocation.SIGNI_CENTER),
                    new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(1)))
                );
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return (CardType.isSIGNI(cardIndexSource.getCardReference().getType()) || CardType.isLRIG(cardIndexSource.getCardReference().getType())) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
