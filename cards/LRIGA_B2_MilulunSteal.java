package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIGA_B2_MilulunSteal extends Card {

    public LRIGA_B2_MilulunSteal()
    {
        setImageSets("WX24-P4-040");

        setOriginalName("ミルルン☆スティール");
        setAltNames("ミルルンスティール Mirurun Sutiiru");
        setDescription("jp",
                "@E：カードを２枚引く。\n" +
                "@E：対戦相手の手札を見て１枚選び、捨てさせる。そのカードがコストの合計が１以下のスペルの場合、対戦相手のトラッシュからそのスペルをコストを支払わずに使用してもよい。"
        );

        setName("en", "Milulun☆Steal");
        setDescription("en",
                "@E: Draw 2 cards.\n" +
                "@E: Look at your opponent's hand, choose 1 card, and discard it. If that card is a spell with a total cost of 1 or less, you may use that spell from your opponent's trash without paying its cost."
        );

		setName("zh_simplified", "米璐璐恩☆偷窃");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n" +
                "@E :看对战对手的手牌选1张，舍弃。那张牌是费用的合计在1以下的魔法的场合，可以从对战对手的废弃区把那张魔法不把费用支付，使用。\n" +
                "（费用的合计是，牌的左上的能量费用的数字的合计。例费用是%W%X:的场合，费用的合计是2）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            draw(2);
        }
        
        private void onEnterEff2()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
            
            if(cardIndex.getLocation() == CardLocation.TRASH &&
               cardIndex.getCardReference().getType() == CardType.SPELL && Cost.getOriginalCostAsNumber(cardIndex.getCardReference()) <= 1 &&
               playerChoiceActivate())
            {
                use(getOwner(), cardIndex, new CostModifier(() -> new EnerCost(Cost.colorless(0)), ModifierMode.SET));
            }
        }
    }
}
