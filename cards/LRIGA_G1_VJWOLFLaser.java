package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_VJWOLFLaser extends Card {
    
    public LRIGA_G1_VJWOLFLaser()
    {
        setImageSets("WXDi-P01-018");
        
        setOriginalName("VJ.WOLF-LASER");
        setAltNames("ブイジェーウルフレーザー Buijee Urufu Reezaa");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からあなたのセンタールリグと共通する色を持つカードを２枚までエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "VJ WOLF - LASER");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put up to two cards that share a color with your center LRIG from among them into your Ener Zone. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "VJ.WOLF - LASER");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put up to 2 cards that share a common color with your center LRIG from among them into the ener zone. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "VJ.WOLF-LASER");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把持有与你的核心分身共通颜色的牌2张最多放置到能量区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromLooked());
            putInEner(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
