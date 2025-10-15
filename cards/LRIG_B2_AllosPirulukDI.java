package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CoinCost;

public final class LRIG_B2_AllosPirulukDI extends Card {

    public LRIG_B2_AllosPirulukDI()
    {
        setImageSets("WDK02-003");

        setOriginalName("アロス・ピルルク　ＤＩ");
        setAltNames("アロスピルルクジ Arosu Piruruku Ji");
        setDescription("jp",
                "@E #C：カードを１枚引く。"
        );

        setName("en", "Allos Piruluk DI");
        setDescription("en",
                "@E #C: Draw 1 card."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 DI");
        setDescription("zh_simplified", 
                "@E #C:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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

            registerEnterAbility(new CoinCost(1), this::onEnterEff);
        }

        private void onEnterEff()
        {
            draw(1);
        }
    }
}
