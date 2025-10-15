package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_B1_AllosPirulukAlpha extends Card {

    public LRIG_B1_AllosPirulukAlpha()
    {
        setImageSets("WXK01-017");

        setOriginalName("アロス・ピルルク　Ａ");
        setAltNames("アロスピルルクアルファ Arosu Piruruku Arufa");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：カードを１枚引く。"
        );

        setName("en", "Allos Piruluk Alpha");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Draw 1 card."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 A");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
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
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
