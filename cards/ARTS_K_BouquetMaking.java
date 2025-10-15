package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_K_BouquetMaking extends Card {

    public ARTS_K_BouquetMaking()
    {
        setImageSets("WX24-P3-039");

        setOriginalName("ブーケ・メイキング");
        setAltNames("ブーケ・メイキング Buuke Meikingu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。あなたのデッキの上からカードを５枚見る。その中から＜悪魔＞のシグニを１枚まで公開し手札に加え、＜悪魔＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。この効果で場に出たシグニのレベル１につき対戦相手のデッキの上からカードを１枚トラッシュに置く。"
        );

        setName("en", "Bouquet Making");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power. Look at the top 5 cards of your deck. Reveal up to 1 <<Devil>> SIGNI from among them, and add it to your hand, put up to 1 <<Devil>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. For each level of the SIGNI put onto the field by this effect, put the top card of your opponent's deck into the trash."
        );

		setName("zh_simplified", "花束·制作");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。从你的牌组上面看5张牌。从中把<<悪魔>>精灵1张最多公开加入手牌，<<悪魔>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。依据这个效果出场的精灵的等级的数量，每有1级就从对战对手的牌组上面把1张牌放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());

            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromLooked().playable()).get();
            if(putOnField(cardIndex))
            {
                millDeck(getOpponent(), cardIndex.getIndexedInstance().getLevelByRef());
            }

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
