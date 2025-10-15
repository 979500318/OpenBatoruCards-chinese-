package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class SPELL_W_GetTarot extends Card {

    public SPELL_W_GetTarot()
    {
        setImageSets("WX24-P3-065");

        setOriginalName("ゲット・タロット");
        setAltNames("ゲットタロット Getto Tarotto");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜宇宙＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番上に戻す。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Get Tarot");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 2 <<Space>> SIGNI from among them, add them to your hand, and put the rest on the top of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "获得·塔罗");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<宇宙>>精灵2张最多公开加入手牌，剩下的任意顺序返回牌组最上面。" +
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

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.SPACE).fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
