package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_R_FierySelection extends Card {

    public ARTS_R_FierySelection()
    {
        setImageSets("WX24-P3-033");

        setOriginalName("取捨炎択");
        setAltNames("シュシャエンタク Shusha Entaku");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで【マジックボックス】としてあなたのシグニゾーンに設置し、＜トリック＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Fiery Selection");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and banish it. Look at the top 5 cards of your deck. Put up to 1 card from among them onto 1 of your SIGNI zones as a [[Magic Box]], reveal 1 <<Trick>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "取舍炎择");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，将其破坏。从你的牌组上面看5张牌。从中把牌1张最多作为[[魔术箱]]在你的精灵区设置，<<トリック>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
            
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ZONE).own().fromLooked()).get();
            putAsMagicBox(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.TRICK).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
