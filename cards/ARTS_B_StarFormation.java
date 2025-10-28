package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_B_StarFormation extends Card {

    public ARTS_B_StarFormation()
    {
        setImageSets("WX25-P1-039");

        setOriginalName("スター・フォーメーション");
        setAltNames("スターフォーメーション Sutaa Foomeeshon");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜原子＞のシグニを１枚まで公開し手札に加え、＜原子＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。その後、この効果で場に出たシグニのレベル以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Star Formation");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal 1 <<Atom>> SIGNI from among them and add it to your hand, put 1 <<Atom>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Then, target 1 of your opponent's SIGNI with level equal to or lower than the level of the SIGNI put onto the field this way, and banish it."
        );

        setName("zh_simplified", "星光·齐射");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<原子>>精灵1张最多公开加入手牌，<<原子>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。然后，这个效果出场的精灵的等级以下的对战对手的精灵1只作为对象，将其破坏。"
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

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ATOM).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            CardIndex cardIndexField = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ATOM).fromLooked().playable()).get();
            boolean wasPutOnField = putOnField(cardIndexField);
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
            
            if(wasPutOnField)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,cardIndexField.getIndexedInstance().getLevel().getValue())).get();
                banish(target);
            }
        }
    }
}

