package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_K2_CodeMazeOkayamaCastle extends Card {

    public SIGNI_K2_CodeMazeOkayamaCastle()
    {
        setImageSets("WX24-P2-091");

        setOriginalName("コードメイズ　オカヤマジョ");
        setAltNames("コードメイズオカヤマジョ Koodo Meizu Okayamajo");
        setDescription("jp",
                "@U $TO $T1：対戦相手の場にあるシグニ１体が他のシグニゾーンに移動したとき、対戦相手の中央のシグニゾーンにあるシグニ１体を対象とし、あなたのエナゾーンから＜迷宮＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Code Maze Okayama Castle");
        setDescription("en",
                "@U $TO $T1: When 1 of your opponent's SIGNI moves to another SIGNI zone, target the SIGNI in your opponent's center SIGNI zone, and you may put 1 <<Labyrinth>> SIGNI from your ener zone into the trash. If you do, until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "迷宫代号 冈山城");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当对战对手的场上的精灵1只往其他的精灵区移动时，对战对手的中央的精灵区的精灵1只作为对象，可以从你的能量区把<<迷宮>>精灵1张放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && !isOwnCard(caller) && caller.isSIGNIOnField() && CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().fromLocation(CardLocation.SIGNI_CENTER)).get();
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.LABYRINTH).fromEner()).get();
                if(trash(cardIndex))
                {
                    gainPower(target, -8000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
