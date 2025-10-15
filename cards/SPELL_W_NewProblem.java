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

public final class SPELL_W_NewProblem extends Card {

    public SPELL_W_NewProblem()
    {
        setImageSets("WXDi-P12-062");

        setOriginalName("ニュー・プロブレム");
        setAltNames("ニュープロブレム Nyuu Puroburemu");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から#Sのシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "New Problem");
        setDescription("en",
                "Look at the top five cards of your deck. Reveal up to two #S SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "New Problem");
        setDescription("en_fan",
                "Look at the top 5 cards of your deck. Reveal up to 2 #S @[Dissona]@ SIGNI from among them, add them to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "全新·问题");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把#S的精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
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
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().dissona().fromLooked());
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
