package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.stock.StockAbilityRide;

public final class LRIG_R1_LaylaAccel extends Card {

    public LRIG_R1_LaylaAccel()
    {
        setImageSets("WXK01-010");

        setOriginalName("レイラ＝アクセル");
        setAltNames("レイラアクセル Reira Akuseru");
        setDescription("jp",
                "=I\n" +
                "@E @[手札を１枚捨てる]@：カードを１枚引く。"
        );

        setName("en", "Layla-Accel");
        setDescription("en",
                "=I\n" +
                "@E @[Discard 1 card from your hand]@: Draw 1 card."
        );

		setName("zh_simplified", "蕾拉=加速");
        setDescription("zh_simplified", 
                "[[骑乘]]（直到回合结束时为止，这只分身在对象的你的<<乗機>>精灵1只搭乘。此是费用%R0的@A能力，$T1 ，这只分身不是驾驶状态的场合能使用。）\n" +
                "@E 手牌1张舍弃:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);

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
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
