package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityRide;

public final class LRIG_R3_LaylaBrake extends Card {

    public LRIG_R3_LaylaBrake()
    {
        setImageSets("WDK01-002");

        setOriginalName("レイラ＝ブレーキ");
        setAltNames("レイラブレーキ Reira Bureeki");
        setDescription("jp",
                "=I\n" +
                "@E %R：#Cを得る。"
        );

        setName("en", "Layla-Brake");
        setDescription("en",
                "=I\n" +
                "@E %R: Gain #C."
        );

		setName("zh_simplified", "蕾拉=制动");
        setDescription("zh_simplified", 
                "[[骑乘]]（直到回合结束时为止，这只分身在对象的你的<<乗機>>精灵1只搭乘。此是费用%R0的@A能力，$T1 ，这只分身不是驾驶状态的场合能使用。）\n" +
                "@E %R得到#C。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(7);

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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            gainCoins(1);
        }
    }
}
