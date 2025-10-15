package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B1_PierceMichaela extends Card {

    public LRIGA_B1_PierceMichaela()
    {
        setImageSets("WXDi-P16-042");

        setOriginalName("突・ミカエラ");
        setAltNames("トツミカエラ Totsu Mikaera");
        setDescription("jp",
                "@E：カードを２枚引く。"
        );

        setName("en", "Michaela, Pierce");
        setDescription("en",
                "@E: Draw two cards."
        );
        
        setName("en_fan", "Pierce Michaela");
        setDescription("en_fan",
                "@E: Draw 2 cards."
        );

		setName("zh_simplified", "突·米卡伊来");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
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
