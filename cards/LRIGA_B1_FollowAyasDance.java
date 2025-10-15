package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_FollowAyasDance extends Card {

    public LRIGA_B1_FollowAyasDance()
    {
        setImageSets("WXDi-P09-033");

        setOriginalName("あーやの踊りについてきてね！");
        setAltNames("アーヤノオドリニツイテキテネ Aaya no Odori ni Tsuite kite ne");
        setDescription("jp",
                "@E：カードを１枚引く。\n" +
                "@E：#C #Cを得る。"
        );

        setName("en", "Aya's Dancing with the Stars!");
        setDescription("en",
                "@E: Draw a card.\n" +
                "@E: Gain #C #C."
        );
        
        setName("en_fan", "Follow Aya's Dance!");
        setDescription("en_fan",
                "@E: Draw 1 card.\n" +
                "@E: Gain #C #C."
        );

		setName("zh_simplified", "亚弥的领舞！");
        setDescription("zh_simplified", 
                "@E :抽1张牌。\n" +
                "@E :得到#C #C。\n" +
                "（玩家保留币的上限是5个）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AYA);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            draw(1);
        }
        
        private void onEnterEff2()
        {
            gainCoins(2);
        }
    }
}
