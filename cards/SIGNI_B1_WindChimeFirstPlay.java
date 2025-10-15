package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_WindChimeFirstPlay extends Card {

    public SIGNI_B1_WindChimeFirstPlay()
    {
        setImageSets("WX24-P2-074");

        setOriginalName("壱ノ遊　フウリン");
        setAltNames("イチノユウフウリン Ichi no Yuu Fuurin");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたの他の＜遊具＞のシグニ１体を場からデッキの一番下に置いてもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Wind Chime, First Play");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may put 1 of your other <<Playground Equipment>> SIGNI from the field on the bottom of your deck. If you do, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "壹之游 风铃");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以把你的其他的<<遊具>>精灵1只从场上放置到牌组最下面。这样做的场合，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).except(getCardIndex())).get();
                if(returnToDeck(cardIndex, DeckPosition.BOTTOM))
                {
                    gainPower(target, -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
