package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_TristanCrimsonGeneral extends Card {
    
    public SIGNI_R2_TristanCrimsonGeneral()
    {
        setImageSets("WXDi-P06-056");
        
        setOriginalName("紅将　トリスタン");
        setAltNames("コウショウトリスタン Koushou Torisutan");
        setDescription("jp",
                "~#：あなたのデッキの上からカードを５枚見る。その中からカードを３枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Tristan, Crimson General");
        setDescription("en",
                "~#Look at the top five cards of your deck. Add up to three cards from among them into your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Tristan, Crimson General");
        setDescription("en_fan",
                "~#Look at the top 5 cards of your deck. Add up to 3 cards from among them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "红将 特里斯坦");
        setDescription("zh_simplified", 
                "~#从你的牌组上面看5张牌。从中把牌3张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
