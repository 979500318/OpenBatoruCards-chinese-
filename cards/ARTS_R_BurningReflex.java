package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.EnerCost;

public final class ARTS_R_BurningReflex extends Card {

    public ARTS_R_BurningReflex()
    {
        setImageSets("WX25-P1-004", "WX25-P1-004U");

        setOriginalName("条炎反射");
        setAltNames("ジョウエンハンシャ Jouen Hansha");
        setDescription("jp",
                "@[ブースト]@ -- %R %X %X %X\n\n" +
                "このターン、次に対戦相手のルリグによってあなたのライフクロス１枚がクラッシュされたとき、対戦相手のライフクロス１枚をクラッシュする。あなたがブーストしていた場合、このターン、次に対戦相手のシグニによってあなたのライフクロス１枚がクラッシュされたとき、対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Burning Reflex");
        setDescription("en",
                "@[Boost]@ -- %R %X %X %X\n\n" +
                "This turn, the next time your opponent's LRIG crushes 1 of your life cloth, crush 1 of your opponent's life cloth. If you used Boost, this turn, the next time your opponent's SIGNI crushes 1 of your life cloth, crush 1 of your opponent's life cloth."
        );

        setName("zh_simplified", "条炎反射");
        setDescription("zh_simplified", 
                "赋能—%R%X %X %X（这张必杀使用时，可以作为使用费用追加把%R%X %X %X支付）\n" +
                "这个回合，当下一次因为对战对手的分身把你的生命护甲1张击溃时，对战对手的生命护甲1张击溃。你赋能的场合，这个回合，当下一次因为对战对手的精灵把你的生命护甲1张击溃时，对战对手的生命护甲1张击溃。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setAdditionalCost(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(3)));
        }

        private void onARTSEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond1);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            
            if(arts.hasPaidAdditionalCost())
            {
                attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond2);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond1(CardIndex caller)
        {
            return isOwnCard(caller) && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    CardType.isLRIG(getEvent().getSourceCardIndex().getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onAttachedAutoEffCond2(CardIndex caller)
        {
            return isOwnCard(caller) && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    CardType.isSIGNI(getEvent().getSourceCardIndex().getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            crush(getOpponent());
            CardAbilities.removePlayerAbility(getAbility());
        }
    }
}

