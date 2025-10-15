package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_G2_VJWOLFSync extends Card {
    
    public LRIGA_G2_VJWOLFSync()
    {
        setImageSets("WXDi-D04-010");
        
        setOriginalName("VJ.WOLF-SYNC");
        setAltNames("ブイジェーウルフシンク Buijee Urufu Shinku");
        setDescription("jp",
                "@E：あなたのライフクロスの一番上を見る。その後、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。\n" +
                "$$2あなたのライフクロスの一番上のカードをデッキに加えてシャッフルする。そうした場合、あなたのデッキの上からカードを２枚ライフクロスに加える。"
        );
        
        setName("en", "VJ WOLF - SYNC");
        setDescription("en",
                "@E: Look at the top card of your Life Cloth. Then, choose one of the following.\n" +
                "$$1 Shuffle your deck and add the top card of your deck to your Life Cloth.\n" +
                "$$2 Shuffle the top card of your Life Cloth into your deck. If you do, add the top two cards of your deck to your Life Cloth."
        );
        
        setName("en_fan", "VJ.WOLF - SYNC");
        setDescription("en_fan",
                "@E: Look at the top card of your life cloth. After that, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Shuffle your deck, and add the top card of your deck to life cloth.\n" +
                "$$2 Add the top card of your life cloth to your deck and shuffle it. If you do, add the top 2 cards of your deck to life cloth."
        );
        
		setName("zh_simplified", "VJ.WOLF-SYNC");
        setDescription("zh_simplified", 
                "@E :看你的生命护甲最上面。然后，从以下的2种选1种。\n" +
                "$$1 你的牌组洗切把最上面的牌加入生命护甲。\n" +
                "$$2 你的生命护甲最上面的牌加入牌组洗切。这样做的场合，从你的牌组上面把2张牌加入生命护甲。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(4));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            CardIndex cardIndex = look(CardLocation.LIFE_CLOTH);
            
            if(playerChoiceMode() == 1)
            {
                addToLifeCloth(cardIndex);
                shuffleDeck();
                
                addToLifeCloth(1);
            } else {
                boolean returnedSuccess = returnToDeck(cardIndex, DeckPosition.TOP);
                
                shuffleDeck();
                
                if(returnedSuccess) addToLifeCloth(2);
            }
        }
    }
}
