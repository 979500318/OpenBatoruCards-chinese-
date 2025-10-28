package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_B_DrawFour extends Card {

    public ARTS_B_DrawFour()
    {
        setImageSets("WXK01-019", "PR-K023");

        setOriginalName("ドロー・フォー");
        setAltNames("ドローフォー Doroo Foo");
        setDescription("jp",
                "カードを４枚引く。"
        );

        setName("en", "Draw Four");
        setDescription("en",
                "Draw 4 cards."
        );

        setName("zh_simplified", "抽·四");
        setDescription("zh_simplified", 
                "抽4张牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY);
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
            draw(4);
        }
    }
}
