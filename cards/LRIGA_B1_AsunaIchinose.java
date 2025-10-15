package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_AsunaIchinose extends Card {

    public LRIGA_B1_AsunaIchinose()
    {
        setImageSets("WXDi-CP02-031");

        setOriginalName("一之瀬アスナ");
        setAltNames("イチノセアスナ Ichinose Asuna");
        setDescription("jp",
                "@E：カードを２枚引く。"
        );

        setName("en", "Ichinose Asuna");
        setDescription("en",
                "@E: Draw two cards."
        );
        
        setName("en_fan", "Asuna Ichinose");
        setDescription("en_fan",
                "@E: Draw 2 cards."
        );

		setName("zh_simplified", "一之濑明日奈");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CC);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            draw(2);
        }
    }
}

