package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class ARTS_G_CalmStringsClearPipes extends Card {

    public ARTS_G_CalmStringsClearPipes()
    {
        setImageSets("WX24-P1-026");

        setOriginalName("緩絃朗笛");
        setAltNames("アニマルオーダー Animaru Oodaa Animal Order");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜地獣＞のシグニを１枚まで公開し手札に加え、＜地獣＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。ターン終了時まで、この方法で場に出たシグニは【ランサー】を得る。"
        );

        setName("en", "Calm Strings, Clear Pipes");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 1 <<Earth Beast>> SIGNI from among them, and add it to your hand, put up to 1 <<Earth Beast>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Until end of turn, the SIGNI put onto the field this way gains [[Lancer]]."
        );

        setName("zh_simplified", "缓弦朗笛");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<地兽>>精灵1张最多公开加入手牌，<<地兽>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。直到回合结束时为止，这个方法出场的精灵得到[[枪兵]]。"
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
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).fromLooked().playable()).get();
            if(putOnField(cardIndex)) attachAbility(cardIndex, new StockAbilityLancer(), ChronoDuration.turnEnd());
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
    }
}

