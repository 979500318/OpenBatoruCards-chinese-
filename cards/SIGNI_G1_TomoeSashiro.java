package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G1_TomoeSashiro extends Card {

    public SIGNI_G1_TomoeSashiro()
    {
        setImageSets("WX25-CP1-074");

        setOriginalName("佐城トモエ");
        setAltNames("サシロトモエ Sashiro Tomoe");
        setDescription("jp",
                "@E：あなたの他の＜ブルアカ＞のシグニを１体まで対象とし、ターン終了時まで、それのパワーを＋3000し、それは@>@C：このシグニは対戦相手にダメージを与えない。@@と@>@U：このシグニがアタックしたとき、【エナチャージ１】をする。@@を得る。" +
                "~{{C：[[シャドウ（パワー8000以下のシグニ）]]"
        );

        setName("en", "Sashiro Tomoe");

        setName("en_fan", "Tomoe Sashiro");
        setDescription("en",
                "@E: Target up to 1 of your other <<Blue Archive>> SIGNI, and until end of turn, it gets +3000 power, and it gains:" +
                "@>@C: This SIGNI can't damage your opponent.\n" +
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@" +
                "~{{C: [[Shadow (SIGNI with power 8000 or less)]]"
        );

		setName("zh_simplified", "佐城智惠");
        setDescription("zh_simplified", 
                "@E :你的其他的<<ブルアカ>>精灵1只最多作为对象，直到回合结束时为止，其的力量+3000，其得到\n" +
                "@>@C :这只精灵不会给予对战对手伤害。@@\n" +
                "@>@U :当这只精灵攻击时，[[能量填充1]]。@@\n" +
                "~{{C:[[暗影（力量8000以下的精灵）]]（这只精灵不会被对战对手的力量8000以下的精灵作为对象）@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
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

            registerEnterAbility(this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            
            if(target != null)
            {
                gainPower(target, 3000, ChronoDuration.turnEnd());
                
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_DEAL_DAMAGE, data -> RuleCheckState.BLOCK));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setNestedDescriptionOffset(1);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(1);
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
