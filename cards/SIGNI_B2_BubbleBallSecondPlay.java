package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_BubbleBallSecondPlay extends Card {

    public SIGNI_B2_BubbleBallSecondPlay()
    {
        setImageSets("WX24-P4-073");

        setOriginalName("弍ノ遊　バブルボール");
        setAltNames("ニノユウバブルボール Ni no Yuu Baburu Booru");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにこのシグニがあなたの効果によって場に出ていた場合、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。" +
                "~#：対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Bubble Ball, Second Play");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI entered the field by your effect this turn, target 1 of your opponent's SIGNI, and put it on the bottom of their deck." +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "贰之游 泡泡足球");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合这只精灵因为你的效果出场的场合，对战对手的精灵1只作为对象，将其放置到牌组最下面。" +
                "~#对战对手的分身1只作为对象，将其#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ENTER && event.getCaller().getInstanceId() == getInstanceId() && event.getSourceAbility() != null && isOwnCard(event.getSource())) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
