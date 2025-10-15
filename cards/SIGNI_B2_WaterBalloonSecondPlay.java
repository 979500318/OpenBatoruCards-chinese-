package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B2_WaterBalloonSecondPlay extends Card {

    public SIGNI_B2_WaterBalloonSecondPlay()
    {
        setImageSets("WX24-P2-077");

        setOriginalName("弍ノ遊　ミズフウセン");
        setAltNames("ニノユウミズフウセン Ni no Yuu Mizu Fuusen");
        setDescription("jp",
                "@U：あなたのアタックフェイズの間、このシグニが場を離れたとき、あなたの手札からレベル１の＜遊具＞のシグニ１枚をダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。\n" +
                "@U：あなたのターン終了時、このターンにあなたの効果によってこのシグニが場に出ていた場合、カードを１枚引くか、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Water Balloon, Second Play");
        setDescription("en",
                "@U: During your attack phase, when this SIGNI leaves the field, you may put 1 level 1 <<Playground Equipment>> SIGNI from your hand onto the field downed. That SIGNI's @E abilities don't activate.\n" +
                "@U: At the end of your turn, if this SIGNI entered the field by your effect this turn, draw 1 card or your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "贰之游 水气球");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段期间，当这只精灵离场时，可以从你的手牌把等级1的<<遊具>>精灵1张以#D状态出场。那只精灵的@E能力不能发动。\n" +
                "@U :你的回合结束时，这个回合因为你的效果把这只精灵出场的场合，抽1张牌或，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond2);
        }
        
        private ConditionState onAutoEffCond1()
        {
            return isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).withLevel(1).fromHand().playable()).get();
            putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
        }
        
        private ConditionState onAutoEffCond2()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ENTER && event.getCaller().getInstanceId() == getInstanceId() && event.getSourceAbility() != null && isOwnCard(event.getSource())) > 0)
            {
                if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
                {
                    draw(1);
                } else {
                    discard(getOpponent(), 1);
                }
            }
        }
    }
}
