package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_AlfouIceberg extends Card {

    public LRIGA_K1_AlfouIceberg()
    {
        setImageSets("WXDi-P15-038");

        setOriginalName("アルフォウアイスバーグ");
        setAltNames("Arufou Aisubeegu");
        setDescription("jp",
                "@E：対戦相手のデッキの上からカードを８枚トラッシュに置く。"
        );

        setName("en", "Alfou Iceberg");
        setDescription("en",
                "@E: Put the top eight cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Alfou Iceberg");
        setDescription("en_fan",
                "@E: Put the top 8 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "阿尔芙冰山玫瑰");
        setDescription("zh_simplified", 
                "@E :从对战对手的牌组上面把8张牌放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
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
            millDeck(getOpponent(), 8);
        }
    }
}
