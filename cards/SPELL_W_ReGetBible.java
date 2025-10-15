package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class SPELL_W_ReGetBible extends Card {

    public SPELL_W_ReGetBible()
    {
        setImageSets("WX24-D1-25");

        setOriginalName("リゲット・バイブル");
        setAltNames("リゲットバイブル Rigetto Baiburu Reget");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中からレベル２以下のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。 &E５枚以上@0代わりにその中からシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Re-Get Bible");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 2 level 2 or lower SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order. &E5 or more@0 Instead, reveal up to 2 SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "再获得·圣书");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把等级2以下的精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。&E5张以上@0作为替代，从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff).setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            look(5);
            
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked();
            if(!getAbility().isRecollectFulfilled()) filter = filter.withLevel(0,2);
            DataTable<CardIndex> data = playerTargetCard(0,2, filter);
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
