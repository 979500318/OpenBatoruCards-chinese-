package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_G_WritingYouABriefMessage extends Card {

    public ARTS_G_WritingYouABriefMessage()
    {
        setImageSets("WX24-P3-037");

        setOriginalName("一筆啓上");
        setAltNames("ブラッシュスラッシュ Burasshu Surasshu Brush Slash");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをエナゾーンに置く。あなたのデッキの上からカードを５枚見る。その中から＜美巧＞のシグニを１枚まで公開し手札に加え、＜美巧＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。次の対戦相手のターン終了時まで、この効果で場に出たシグニのパワーを＋3000する。"
        );

        setName("en", "Writing You a Brief Message");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and put it into the ener zone. Look at the top 5 cards of your deck. Reveal up to 1 <<Beautiful Technique>> SIGNI from among them, and add it to your hand, put up to 1 <<Beautiful Technique>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Until the end of your opponent's next turn, the SIGNI put onto the field by this effect gets +3000 power."
        );

		setName("zh_simplified", "一笔启上");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其放置到能量区。从你的牌组上面看5张牌。从中把<<美巧>>精灵1张最多公开加入手牌，<<美巧>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。直到下一个对战对手的回合结束时为止，这个效果出场的精灵的力量+3000。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,8000)).get();
            putInEner(target);

            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromLooked().playable()).get();
            if(putOnField(cardIndex))
            {
                gainPower(cardIndex, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
            }

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
