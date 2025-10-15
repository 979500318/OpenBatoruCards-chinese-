package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_EldoraSearcher extends Card {

    public LRIGA_B1_EldoraSearcher()
    {
        setImageSets("WXDi-P12-034");

        setOriginalName("エルドラ！サーチャー！");
        setAltNames("エルドラサーチャー Erudora Saachaa Eldora Searcher");
        setDescription("jp",
                "@E：カードを１枚引く。\n" +
                "@E：あなたのライフクロスの一番上を見る。そのカードをデッキに加えてシャッフルしてもよい。そうした場合、あなたのデッキの一番上のカードをライフクロスに加える。"
        );

        setName("en", "Eldora! Searcher!");
        setDescription("en",
                "@E: Draw a card.\n@E: Look at the top card of your Life Cloth. You may shuffle that card into your deck. If you do, add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "Eldora! Searcher!");
        setDescription("en_fan",
                "@E: Draw 1 card.\n" +
                "@E: Look at the top card of your life cloth. You may shuffle that card into your deck. If you do, add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "艾尔德拉！检索！");
        setDescription("zh_simplified", 
                "@E :抽1张牌。\n" +
                "@E :看你的生命护甲最上面。可以把那张牌加入牌组洗切。这样做的场合，你的牌组最上面的牌加入生命护甲。\n"
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            draw(1);
        }

        private void onEnterEff2()
        {
            CardIndex cardIndex = look(CardLocation.LIFE_CLOTH);
            
            if(cardIndex != null && playerChoiceActivate() && returnToDeck(cardIndex, DeckPosition.TOP) && shuffleDeck())
            {
                addToLifeCloth(1);
            } else {
                addToLifeCloth(cardIndex);
            }
        }
    }
}
