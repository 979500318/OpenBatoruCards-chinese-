package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_ProtoEnergeSmallEquipment extends Card {
    
    public SIGNI_W1_ProtoEnergeSmallEquipment()
    {
        setImageSets("WXDi-P08-050");
        setLinkedImageSets("WXDi-P08-037");
        
        setOriginalName("小装　プロトエナジェ");
        setAltNames("ショウソウプロトエナジェ Shousou Puroto Enaje");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から《大装　タマ//メモリア》を１枚まで公開し手札に加え、カード１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Proto Energe, Lightly Armed");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Reveal up to one \"Tama//Memoria, Full Armed\" from among them and add it to your hand. Put one of the remaining cards on top of your deck, and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Proto Energe, Small Equipment");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Reveal up to 1 \"Tama//Memoria, Great Equipment\" from among them, and add it to your hand, and return 1 card from among them to the top of your deck, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "小装 原型源能");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把《大装　タマ//メモリア》1张最多公开加入手牌，1张牌返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(2000);
        
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
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withName("大装　タマ//メモリア").fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, Deck.DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, Deck.DeckPosition.BOTTOM);
            }
        }
    }
}
