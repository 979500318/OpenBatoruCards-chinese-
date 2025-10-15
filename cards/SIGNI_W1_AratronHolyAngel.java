package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W1_AratronHolyAngel extends Card {

    public SIGNI_W1_AratronHolyAngel()
    {
        setImageSets("WX25-P1-064");

        setOriginalName("聖天　アラトロン");
        setAltNames("セイテンアラトロン Seiten Aratoron");
        setDescription("jp",
                "@E @[手札から＜天使＞のシグニを１枚捨てる]@：あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Aratron, Holy Angel");
        setDescription("en",
                "@E @[Discard 1 <<Angel>> SIGNI from your hand]@: Look at the top 5 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "圣天 阿拉特隆");
        setDescription("zh_simplified", 
                "@E 从手牌把<<天使>>精灵1张舍弃:从你的牌组上面看5张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
