package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_HinataWakaba extends Card {

    public SIGNI_W1_HinataWakaba()
    {
        setImageSets("WX25-CP1-052");

        setOriginalName("若葉ヒナタ");
        setAltNames("ワカバヒナタ Wakaba Hinata");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー3000以下のシグニ１体を対象とし、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、それを手札に戻す。" +
                "~{{C $TP：[[シャドウ（レベル２以下）]]"
        );

        setName("en", "Wakaba Hinata");

        setName("en_fan", "Hinata Wakaba");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 3000 or less, and you may discard 1 <<Blue Archive>> card from your hand. If you do, return it to their hand." +
                "~{{C $TP: [[Shadow (level 2 or lower)]]."
        );

		setName("zh_simplified", "若叶日向");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量3000以下的精灵1只作为对象，可以从手牌把<<ブルアカ>>牌1张舍弃。这样做的场合，将其返回手牌。\n" +
                "~{{C$TP :[[暗影（等级2以下）]]@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,3000)).get();
            
            if(target != null && discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
            {
                addToHand(target);
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
