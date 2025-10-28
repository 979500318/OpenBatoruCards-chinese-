package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_B_FlagWaving extends Card {

    public ARTS_B_FlagWaving()
    {
        setImageSets("WX25-P2-040");

        setOriginalName("フラッグ・ウェービング");
        setAltNames("フラッグウェービング Furaggu Ueebingu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それをダウンする。カードを２枚引き、手札を１枚捨てる。"
        );

        setName("en", "Flag Waving");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and down it. Draw 2 cards, and discard 1 card from your hand."
        );

        setName("zh_simplified", "旗帜·飘扬");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其横置。抽2张牌，手牌1张舍弃。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
            
            draw(2);
            discard(1);
        }
    }
}
