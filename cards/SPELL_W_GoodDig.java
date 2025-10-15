package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SPELL_W_GoodDig extends Card {
    
    public SPELL_W_GoodDig()
    {
        setImageSets("WXDi-D04-021");
        
        setOriginalName("グッド・ディグ");
        setAltNames("グッドディグ Guddo Digu");
        setDescription("jp",
                "あなたのデッキの上からカードを７枚見る。その中からシグニを２枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。あなたの場に＜Card Jockey＞のルリグが３体いる場合、代わりにその中からカードを２枚まで手札に加え、残りをシャッフルしてデッキの一番下に置く。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Good Dig");
        setDescription("en",
                "Look at the top seven cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in a random order. If you have three <<Card Jockey>> LRIG on your field, instead add up to two cards from among them to your hand and put the rest on the bottom of your deck in a random order." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Good Dig");
        setDescription("en_fan",
                "Look at the top 7 cards of your deck. Reveal up to 2 SIGNI from among them and add them to your hand, and shuffle the rest and put them on the bottom of your deck. If there are 3 <<Card Jockey>> LRIGs on your field, instead add up to 2 cards from among them to your hand, and shuffle the rest and put them on the bottom of your deck." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "棒棒·点赞");
        setDescription("zh_simplified", 
                "从你的牌组上面看7张牌。从中把精灵2张最多公开加入手牌，剩下的洗切放置到牌组最下面。你的场上的<<CardJockey>>分身在3只的场合，作为替代，从中把牌2张最多加入手牌，剩下的洗切放置到牌组最下面。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
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
            
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().fromLooked();
            
            DataTable<CardIndex> data = playerTargetCard(0,2, !isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? filter.SIGNI() : filter);
            if(!isLRIGTeam(CardLRIGTeam.CARD_JOCKEY)) reveal(data);
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
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
