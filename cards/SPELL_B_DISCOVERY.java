package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class SPELL_B_DISCOVERY extends Card {
    
    public SPELL_B_DISCOVERY()
    {
        setImageSets("WXDi-P06-068");
        setLinkedImageSets("WXDi-P06-037");
        
        setOriginalName("DISCOVERY");
        setAltNames("ディスカバリー Disukabarii");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から《幻水姫　エルドラ//メモリア》を１枚まで公開し手札に加え、カード１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。" +
                "~#対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );
        
        setName("en", "DISCOVERY");
        setDescription("en",
                "Look at the top five cards of your deck. Reveal up to one \"Eldora//Memoria, Aquatic Phantom Queen\" from among them and add it to your hand. Put one of the remaining cards on top of your deck, and put the rest on the bottom of your deck in any order." +
                "~#Down up to two target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "DISCOVERY");
        setDescription("en_fan",
                "Look at the top 5 cards of your deck. Reveal up to 1 \"Eldora//Memoria, Water Phantom Princess\" from among them, and add it to your hand, return 1 card from from among them to the top of your deck, and put the rest on the bottom of your deck in any order." +
                "~#Target 2 of your opponent's SIGNI, and down them."
        );
        
		setName("zh_simplified", "DISCOVERY");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把《幻水姫　エルドラ//メモリア》1张最多公开加入手牌，1张牌返回牌组最上面，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的精灵2只最多作为对象，将这些#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withName("幻水姫　エルドラ//メモリア").fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}
