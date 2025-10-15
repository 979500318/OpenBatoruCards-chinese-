package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_G3_JackbeansNaturalPlantPrincess extends Card {

    public SIGNI_G3_JackbeansNaturalPlantPrincess()
    {
        setImageSets("WX24-P2-054");
        setLinkedImageSets("WX24-P2-026");

        setOriginalName("羅植姫　ジャックビーンズ");
        setAltNames("ラショクヒメジャックビーンズ Rashokuhime Jakkubiinzu");
        setDescription("jp",
                "@U $TP $T1：あなたのレベル２以上の＜植物＞のシグニ１体が場を離れたとき、あなたのエナゾーンからレベル１の＜植物＞のシグニを１枚まで対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に《参式　一衣》がいる場合、対戦相手のシグニを３体まで対象とし、それらのレベルの合計１につき%Gを支払ってもよい。そうした場合、それらをエナゾーンに置く。"
        );

        setName("en", "Jackbeans, Natural Plant Princess");
        setDescription("en",
                "@U $TP $T1: When 1 of your level 2 or higher <<Plant>> SIGNI leaves the field, target up to 1 level 1 <<Plant>> SIGNI from your ener zone, and put it onto the field. Its @E abilities don't activate.\n" +
                "@U: At the beginning of your attack phase, if your LRIG is \"Hitoe, Third Formula\", target up to 3 of your opponent's SIGNI, and you may pay %G for each of their combined levels. If you do, put them into the ener zone."
        );

		setName("zh_simplified", "罗植姬 杰克魔豆");
        setDescription("zh_simplified", 
                "@U $TP $T1 当你的等级2以上的<<植物>>精灵1只离场时，从你的能量区把等级1的<<植物>>精灵1张最多作为对象，将其出场。其的@E能力不能发动。\n" +
                "@U 你的攻击阶段开始时，你的场上有《参式　一衣》的场合，对战对手的精灵3只最多作为对象，可以依据这些的等级的合计的数量，每有1级就把%G:支付。这样做的场合，将这些放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond2);
        }

        private ConditionState onAutoEffCond1(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) && caller.isSIGNIOnField() && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) &&
                    caller.getIndexedInstance().getLevel().getValue() >= 2 && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PLANT) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(1).withClass(CardSIGNIClass.PLANT).fromEner().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
        
        private ConditionState onAutoEffCond2()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("参式　一衣"))
            {
                DataTable<CardIndex> targets = playerTargetCard(0,3, new TargetFilter(TargetHint.ENER).OP().SIGNI());
                
                if(targets.get() != null)
                {
                    int totalLevels = targets.stream().mapToInt(cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue()).sum();
                    if(payEner(Cost.color(CardColor.GREEN, totalLevels)))
                    {
                        putInEner(targets);
                    }
                }
            }
        }
    }
}
