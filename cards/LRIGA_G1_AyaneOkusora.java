package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_AyaneOkusora extends Card {

    public LRIGA_G1_AyaneOkusora()
    {
        setImageSets("WXDi-CP02-038");

        setOriginalName("奥空アヤネ");
        setAltNames("オクソラアヤネ Okusora Ayane");
        setDescription("jp",
                "@E：【エナチャージ２】"
        );

        setName("en", "Okusora Ayane");
        setDescription("en",
                "@E: [[Ener Charge 2]]. "
        );
        
        setName("en_fan", "Ayane Okusora");
        setDescription("en_fan",
                "@E: [[Ener Charge 2]]"
        );

		setName("zh_simplified", "奥空绫音");
        setDescription("zh_simplified", 
                "@E :[[能量填充2]]（从你的牌组上面把2张牌放置到能量区）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.FORECLOSURE_TASK_FORCE);
        setColor(CardColor.GREEN);
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
            enerCharge(2);
        }
    }
}

