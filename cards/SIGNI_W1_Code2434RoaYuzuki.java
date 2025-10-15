package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_Code2434RoaYuzuki extends Card {
    
    public SIGNI_W1_Code2434RoaYuzuki()
    {
        setImageSets("WXDi-P00-043");
        setLinkedImageSets("WXDi-P00-033");
        
        setOriginalName("コード２４３４　夢月ロア");
        setAltNames("コードニジサンジユヅキロア Koodo Nijisanji Yudzuki Roa");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚見る。その中から《コード２４３４　アルス・アルマル》１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Roa Yuzuki, Code 2434");
        setDescription("en",
                "@E: Look at the top two cards of your deck. You may reveal a \"Ars Almal, Code 2434\" from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Look at the top three cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code 2434 Roa Yuzuki");
        setDescription("en_fan",
                "@E: Look at the top 2 cards of your deck. Reveal up to 1 \"Code 2434 Ars Almal\" from among them, add it to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Look at the top 3 cards of your deck. Reveal up to 2 SIGNI from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "2434代号 梦月萝娅 ");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看2张牌。从中把《コード２４３４　アルス・アルマル》1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#从你的牌组上面看3张牌。从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(2000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(2);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withName("コード２４３４　アルス・アルマル").fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
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
