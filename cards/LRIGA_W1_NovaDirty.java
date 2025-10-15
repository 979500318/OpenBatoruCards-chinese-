package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_NovaDirty extends Card {
    
    public LRIGA_W1_NovaDirty()
    {
        setImageSets("WXDi-D05-006");
        
        setOriginalName("ノヴァ＝ダーティ");
        setAltNames("ノヴァダーティ Nova Daati");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを７枚見る。その中から白と青のシグニをそれぞれ１枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );
        
        setName("en", "Nova =Dirty=");
        setDescription("en",
                "@E: Look at the top seven cards of your deck. Reveal up to one white SIGNI and one blue SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "Nova-Dirty");
        setDescription("en_fan",
                "@E: Look at the top 7 cards of your deck. Reveal up to 1 white and blue SIGNI each from among them, and add them to your hand, and shuffle the rest and put them on the bottom of your deck."
        );
        
		setName("zh_simplified", "超=恶劣");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看7张牌。从中把白色和蓝色的精灵各1张最多公开加入手牌，剩下的洗切放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final CardColor[] COLORS = {CardColor.WHITE, CardColor.BLUE};
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(7);
            
            DataTable<CardIndex> data = new DataTable<>();
            for(CardColor color : COLORS)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().except(data).withColor(color).fromLooked()).get();
                if(cardIndex != null) data.add(cardIndex);
            }
            reveal(data);
            addToHand(data);
            
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
