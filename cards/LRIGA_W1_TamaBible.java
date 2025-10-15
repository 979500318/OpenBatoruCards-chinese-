package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_TamaBible extends Card {
    
    public LRIGA_W1_TamaBible()
    {
        setImageSets("WXDi-P08-023");
        
        setOriginalName("タマ・ばいぶる");
        setAltNames("タマバイブル Tama Baiburu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを７枚見る。その中からシグニを１枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );
        
        setName("en", "Tama Bible");
        setDescription("en",
                "@E: Look at the top seven cards of your deck. Reveal up to one SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "Tama Bible");
        setDescription("en_fan",
                "@E: Look at the top 7 cards of your deck. Reveal up to 1 card from among them, add it to your hand, and shuffle the rest and put them on the bottom of your deck."
        );
        
		setName("zh_simplified", "小玉·圣典");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看7张牌。从中把精灵1张最多公开加入手牌，剩下的洗切放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(7);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            int countLooked = getLookedCount();
            if(countLooked > 0)
            {
                forEachCardInLooked(cardIndexLooked -> {
                    returnToDeck(cardIndexLooked, DeckPosition.BOTTOM);
                });
                shuffleDeck(countLooked, DeckPosition.BOTTOM);
            }
        }
    }
}
