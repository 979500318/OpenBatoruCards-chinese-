package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_TawilEibon extends Card {
    
    public LRIGA_R1_TawilEibon()
    {
        setImageSets("WXDi-P00-010");
        
        setOriginalName("タウィル＝エイボン");
        setAltNames("タウィルエイボン Tauiru Eibon");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から赤と青と緑のシグニをそれぞれ１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Tawil =Eibon=");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Reveal up to one red SIGNI, one blue SIGNI, and one green SIGNI from among them and add each of them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Tawil-Eibon");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Reveal up to 1 red, blue, and green SIGNI each from among them, and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "塔维尔=艾本");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把红色和蓝色和绿色的精灵各1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(+0);
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
        
        private final CardColor[] COLORS = {CardColor.RED,CardColor.BLUE,CardColor.GREEN};
        private void onEnterEff()
        {
            look(3);
            
            DataTable<CardIndex> data = new DataTable<>();
            for(CardColor color : COLORS)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().except(data).fromLooked().withColor(color)).get();
                if(cardIndex != null) data.add(cardIndex);
            }
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
