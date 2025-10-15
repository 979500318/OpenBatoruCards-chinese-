package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class ARTS_B_Hacking extends Card {

    public ARTS_B_Hacking()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-027");

        setOriginalName("ハッキング");
        setAltNames("Hakkingu");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜ブルアカ＞のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。その後、この方法で青の＜ブルアカ＞のカードを１枚以上手札に加えた場合、対戦相手のパワー12000以下のシグニ１体を対象とし、対戦相手が手札を２枚捨てないかぎり、それをバニッシュする。"
        );

        setName("en", "Hacking");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 2 <<Blue Archive>> cards from among them, add them to your hand, and put the rest on the bottom of your deck in any order. Then, if you added 1 or more blue <<Blue Archive>> cards to your hand this way, target 1 of your opponent's SIGNI with power 12000 or less, and banish it unless your opponent discards 2 cards from their hand."
        );

		setName("zh_simplified", "骇客攻击");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<ブルアカ>>牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。然后，这个方法把蓝色的<<ブルアカ>>牌1张以上加入手牌的场合，对战对手的力量12000以下的精灵1只作为对象，如果对战对手不把手牌2张舍弃，那么将其破坏。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked());
            reveal(data);
            boolean match = data.get() != null && data.stream().anyMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.BLUE));
            addToHand(data);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }

            if(match)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                
                if(target != null && discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).get() == null)
                {
                    banish(target);
                }
            }
        }
    }
}
