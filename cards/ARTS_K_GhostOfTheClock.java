package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class ARTS_K_GhostOfTheClock extends Card {

    public ARTS_K_GhostOfTheClock()
    {
        setImageSets("WX25-P1-044");

        setOriginalName("ゴースト・オブ・クロック");
        setAltNames("ゴーストオブクロック Goosuto Obu Kurokku");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜怪異＞のシグニを１枚まで公開し手札に加え、＜怪異＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。ターン終了時まで、この効果で場に出たシグニは[[アサシン（パワー10000以下のシグニ）]]を得る。"
        );

        setName("en", "Ghost of the Clock");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal 1 <<Apparition>> SIGNI from among them, add it to your hand, put 1 <<Apparition>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Until end of turn, the SIGNI put onto the field this way gains [[Assassin (SIGNI with power 10000 or less)]]."
        );

        setName("zh_simplified", "幽灵·时光");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<怪异>>精灵1张最多公开加入手牌，<<怪异>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。直到回合结束时为止，这个效果出场的精灵得到[[暗杀（力量10000以下的精灵）]]。"
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
            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.APPARITION).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.APPARITION).fromLooked().playable()).get();
            if(putOnField(cardIndex))
            {
                attachAbility(cardIndex, new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
            }

            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}

