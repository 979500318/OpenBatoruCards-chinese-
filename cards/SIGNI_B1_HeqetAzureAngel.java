package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_HeqetAzureAngel extends Card {
    
    public SIGNI_B1_HeqetAzureAngel()
    {
        setImageSets("WXDi-P02-064", "SPDi01-61");
        
        setOriginalName("蒼天　ヘケト");
        setAltNames("ソウテンヘケト Souten Heketo");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から好きな枚数のカードを好きな順番でデッキの一番下に置き、残りを好きな順番でデッキの一番上に戻す。"
        );
        
        setName("en", "Heqet, Azure Angel");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Put any number of them on the bottom of your deck in any order and the rest on top of your deck in any order."
        );
        
        setName("en_fan", "Heqet, Azure Angel");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Put any number of cards from among them on the bottom of your deck in any order, and return the rest to the top of your deck in any order."
        );
        
		setName("zh_simplified", "苍天 海奎特");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把任意张数的牌任意顺序放置到牌组最下面，剩下的任意顺序返回牌组最上面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);
        
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
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.BOTTOM).own().fromLooked());
            returnToDeck(data, DeckPosition.BOTTOM);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
