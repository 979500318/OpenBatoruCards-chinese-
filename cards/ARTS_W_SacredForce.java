package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;

public final class ARTS_W_SacredForce extends Card {

    public ARTS_W_SacredForce()
    {
        setImageSets("WX24-P1-001", "WX24-P1-001U");
        setLinkedImageSets(Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("セイクリッド・フォース");
        setAltNames("セイクリッドフォース Seikuriddo Foosu");
        setDescription("jp",
                "以下の３つから２つまで選ぶ。\n" +
                "$$1【ルリグバリア】１つを得る。\n" +
                "$$2対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$3あなたのデッキの上からカードを７枚見る。その中からカードを２枚まで手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Sacred Force");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Gain 1 [[LRIG Barrier]].\n" +
                "$$2 Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "$$3 Look at the top 7 cards of your deck. Add up to 2 cards from among them to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "圣白·原力");
        setDescription("zh_simplified", 
                "从以下的3种选2种最多。\n" +
                "$$1 得到[[分身屏障]]1个。\n" +
                "$$2 对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "$$3 从你的牌组上面看7张牌。从中把牌2张最多加入手牌，剩下的洗切放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

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
            arts.setModeChoice(0,2);
        }
        
        private void onARTSEff()
        {
            int modes = arts.getChosenModes();
            
            if((modes & 1<<0) != 0)
            {
                attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
            if((modes & 1<<2) != 0)
            {
                look(7);
                
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
                addToHand(data);
                
                int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
                shuffleDeck(getOwner(), countReturned, DeckPosition.BOTTOM);
            }
        }
    }
}

