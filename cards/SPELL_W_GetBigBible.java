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
import open.batoru.data.Cost;

public final class SPELL_W_GetBigBible extends Card {
    
    public SPELL_W_GetBigBible()
    {
        setImageSets("WXDi-D08-022");
        
        setOriginalName("ゲット・ビッグバイブル");
        setAltNames("ゲットビッグバイブル Getto Biggu Baiburu");
        setDescription("jp",
                "あなたのデッキの上からカードを７枚見る。その中からシグニを２枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Get Big Bible");
        setDescription("en",
                "Look at the top seven cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in a random order." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Get Big Bible");
        setDescription("en_fan",
                "Look at the top 7 cards of your deck. Reveal up to 2 SIGNI from among them and add them to your hand, and shuffle the rest and put them on the bottom of your deck." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "获得·大圣书");
        setDescription("zh_simplified", 
                "从你的牌组上面看7张牌。从中把精灵2张最多公开加入手牌，剩下的洗切放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        
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
            look(7);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
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
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
