package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class ARTS_B_BayonetShot extends Card {

    public ARTS_B_BayonetShot()
    {
        setImageSets("WX25-P2-039");

        setOriginalName("バヨネット・ショット");
        setAltNames("バヨネットショット Bayonetto Shotto");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜武勇＞のシグニを１枚まで公開し手札に加え、＜武勇＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。ターン終了時まで、この方法で場に出たシグニは[[アサシン（凍結状態のパワー12000以下のシグニ）]]を得る。"
        );

        setName("en", "Bayonet Shot");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal 1 <<Valor>> SIGNI from among them, add it to your hand, put 1 <<Valor>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Until end of turn, the SIGNI put onto the field this way gains [[Assassin (frozen SIGNI with power 12000 or less)]]."
        );

        setName("zh_simplified", "刺刀·狙击");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<武勇>>精灵1张最多公开加入手牌，<<武勇>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。直到回合结束时为止，这个方法出场的精灵得到[[暗杀（冻结状态的力量12000以下的精灵）]]。"
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
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromLooked().playable()).get();
            if(putOnField(cardIndex))
            {
                attachAbility(cardIndex, new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
            }
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().isState(CardStateFlag.FROZEN) &&
                   cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 12000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}

