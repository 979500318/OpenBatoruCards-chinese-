package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.stock.StockAbilityRide;

public final class LRIG_R2_LaylaCorner extends Card {

    public LRIG_R2_LaylaCorner()
    {
        setImageSets("WDK01-003");

        setOriginalName("レイラ＝コーナー");
        setAltNames("レイラコーナー Reira Koona");
        setDescription("jp",
                "=I\n" +
                "@E #C：カードを１枚引く。"
        );

        setName("en", "Layla-Corner");
        setDescription("en",
                "=I\n" +
                "@E #C: Draw 1 card."
        );

		setName("zh_simplified", "蕾拉=拐弯");
        setDescription("zh_simplified", 
                "[[骑乘]]（直到回合结束时为止，这只分身在对象的你的<<乗機>>精灵1只搭乘。此是费用%R0的@A能力，$T1 ，这只分身不是驾驶状态的场合能使用。）\n" +
                "@E #C:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(4);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityRide());
            
            registerEnterAbility(new CoinCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
