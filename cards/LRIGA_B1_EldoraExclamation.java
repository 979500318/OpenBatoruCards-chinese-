package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_EldoraExclamation extends Card {

    public LRIGA_B1_EldoraExclamation()
    {
        setImageSets("WXDi-P12-033");

        setOriginalName("エルドラ！エクスクラメーション！");
        setAltNames("エルドラエクスクラメーション Erudora Ekusukurameenshon Eldora Exclamation");
        setDescription("jp",
                "@E：カードを２枚引く。"
        );

        setName("en", "Eldora! Exclamation!");
        setDescription("en",
                "@E: Draw two cards."
        );
        
        setName("en_fan", "Eldora! Exclamation!");
        setDescription("en_fan",
                "@E: Draw 2 cards."
        );

		setName("zh_simplified", "艾尔德拉！惊讶！");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
