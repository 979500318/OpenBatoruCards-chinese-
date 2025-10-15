package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_LaylaTheCrack extends Card {

    public LRIGA_R2_LaylaTheCrack()
    {
        setImageSets("WXDi-P12-030");

        setOriginalName("レイラ・ザ・クラック");
        setAltNames("レイラザクラック Reira Za Kurakku");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U $T1：対戦相手のシグニによってあなたのライフクロス１枚がクラッシュされたとき、対戦相手のライフクロス１枚をクラッシュする。@@を得る。\n" +
                "@E %R %X：ターン終了時まで、このルリグは@>@U $T1：対戦相手のルリグによってあなたのライフクロス１枚がクラッシュされたとき、対戦相手のライフクロス１枚をクラッシュする。@@を得る。"
        );

        setName("en", "Layla the Crack");
        setDescription("en",
                "@E: This LRIG gains@>@U $T1: When one of your Life Cloth is crushed by your opponent's SIGNI, crush one of your opponent's Life Cloth.@@until end of turn.\n@E %R %X: This LRIG gains@>@U $T1: When one of your Life Cloth is crushed by your opponent's LRIG, crush one of your opponent's Life Cloth.@@until end of turn."
        );
        
        setName("en_fan", "Layla the Crack");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U $T1: Whenever 1 of your opponent's SIGNI crushes 1 of your life cloth, crush 1 of your opponent's life cloth.@@" +
                "@E %R %X: Until end of turn, this LRIG gains:" +
                "@>@U $T1: Whenever 1 of your opponent's LRIG crushes 1 of your life cloth, crush 1 of your opponent's life cloth."
        );

		setName("zh_simplified", "蕾拉·极·裂解");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当因为对战对手的精灵把你的生命护甲1张击溃时，对战对手的生命护甲1张击溃。@@\n" +
                "@E %R%X:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当因为对战对手的分身把你的生命护甲1张击溃时，对战对手的生命护甲1张击溃。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEff1Cond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   CardType.isSIGNI(getEvent().getSourceCardIndex().getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEff2Cond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   CardType.isLRIG(getEvent().getSourceCardIndex().getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAttachedAutoEff(CardIndex caller)
        {
            crush(getOpponent());
        }
    }
}
