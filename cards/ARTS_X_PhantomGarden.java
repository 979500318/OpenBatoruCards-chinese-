package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;

import java.util.List;

public final class ARTS_X_PhantomGarden extends Card {

    public ARTS_X_PhantomGarden()
    {
        setImageSets("WDK01-010", "PR-K024");

        setOriginalName("ファントム・ガーデン");
        setAltNames("ファントムガーデン Fantomu Gaaden");
        setDescription("jp",
                "@[ベット]@ -- #C\n\n" +
                "あなたのトラッシュからあなたのセンタールリグと共通する色を持つそれぞれレベルの異なるシグニを３枚まで対象とし、それらを手札に加える。あなたがベットしていた場合、３枚の代わりに４枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Phantom Garden");
        setDescription("en",
                "@[Bet]@ -- #C\n\n" +
                "Target up to 3 SIGNI with different levels that share a common color with your center LRIG from your trash, and add them to your hand. If you bet, target up to 4 instead of 3 cards, and add them to your hand."
        );

		setName("zh_simplified", "幻象·花园");
        setDescription("zh_simplified", 
                "下注—#C\n" +
                "（这张必杀使用时可以支付#C）\n" +
                "从你的废弃区把持有与你的核心分身共通颜色的等级不同的精灵3张最多作为对象，将这些加入手牌。你下注的场合，3张，作为替代，4张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.ARTS);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(1));
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0, !arts.hasUsedBet() ? 3 : 4, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash(), this::onARTSEffTargetCond);
            addToHand(data);
        }
        private boolean onARTSEffTargetCond(List<CardIndex> pickedCards)
        {
            return pickedCards.isEmpty() || pickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).distinct().count() == pickedCards.size();
        }
    }
}
