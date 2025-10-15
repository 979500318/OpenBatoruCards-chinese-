package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_AllosPirulukTRI extends Card {

    public LRIG_B3_AllosPirulukTRI()
    {
        setImageSets("WDK02-002");

        setOriginalName("アロス・ピルルク　ＴＲＩ");
        setAltNames("アロスピルルクトリ Arosu Piruruku Tori");
        setDescription("jp",
                "@E %B：#Cを得る。"
        );

        setName("en", "Allos Piruluk TRI");
        setDescription("en",
                "@E %B: Gain #C."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 TRI");
        setDescription("zh_simplified", 
                "@E %B:得到#C。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            gainCoins(1);
        }
    }
}
