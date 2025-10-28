package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_R_ConcentratedCrimsonFlames extends Card {

    public ARTS_R_ConcentratedCrimsonFlames()
    {
        setImageSets("WX24-P2-003", "WX24-P2-003U");

        setOriginalName("集中紅火");
        setAltNames("シュウチュウコウカ Shuuchuu Kouka");
        setDescription("jp",
                "手札をすべて捨て、カードを５枚引く。その後、この効果で捨てた赤のカード１枚につき対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを１枚まで対象とし、それらをトラッシュに置く。\n" +
                "&E４枚以上@0追加で、対戦相手のセンタールリグがレベル３以上の場合、対戦相手のエナゾーンからカードを２枚まで対象とし、それらをトラッシュに置く。"
        );

        setName("en", "Concentrated Crimson Flames");
        setDescription("en",
                "Discard all cards from your hand, and draw 5 cards. Then, for each red card discarded by this effect, target up to 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put them into the trash.\n" +
                "&E4 or more@0 Additionally, if your opponent's center LRIG is level 3 or higher, target up to 2 cards from your opponent's ener zone, and put them into the trash."
        );

        setName("zh_simplified", "集中红火");
        setDescription("zh_simplified", 
                "手牌全部舍弃，抽5张牌。然后，依据这个效果舍弃的红色的牌的数量，每有1张就从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张最多作为对象，将这些放置到废弃区。\n" +
                "&E4张以上@0追加，对战对手的核心分身在等级3以上的场合，从对战对手的能量区把牌2张最多作为对象，将这些放置到废弃区。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
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

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> dataDiscarded = discard(getCardsInHand(getOwner()));
            draw(5);
            
            if(dataDiscarded.get() != null)
            {
                int countRed = (int)dataDiscarded.stream().filter(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.RED)).count();
                if(countRed > 0)
                {
                    DataTable<CardIndex> data = playerTargetCard(0,countRed, new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner());
                    trash(data);
                }
            }
            
            if(getAbility().isRecollectFulfilled() && getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BURN).OP().fromEner());
                trash(data);
            }
        }
    }
}
