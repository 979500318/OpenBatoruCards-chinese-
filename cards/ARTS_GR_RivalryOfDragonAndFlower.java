package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_GR_RivalryOfDragonAndFlower extends Card {

    public ARTS_GR_RivalryOfDragonAndFlower()
    {
        setImageSets("WX24-P4-005","WX24-P4-005U");

        setOriginalName("竜花相搏");
        setAltNames("リュウカソウハク Ryuuka Souhaku");
        setDescription("jp",
                "あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。あなたのライフクロス1枚をクラッシュしてもよい。そうした場合、対戦相手のライフクロスをクラッシュする。"
        );

        setName("en", "Rivalry of Dragon and Flower");
        setDescription("en",
                "Shuffle your deck, and add the top card of your deck to life cloth. You may crush 1 of your life cloth. If you do, crush 1 of your opponent's life cloth."
        );

        setName("zh_simplified", "龙花相搏");
        setDescription("zh_simplified", 
                "你的牌组洗切把最上面的牌加入生命护甲。可以把你的生命护甲1张击溃。这样做的场合，对战对手的生命护甲1张击溃。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED, CardColor.GREEN);
        setCost(Cost.color(CardColor.RED, 1) + Cost.color(CardColor.GREEN, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            shuffleDeck();
            addToLifeCloth(1);

            if(playerChoiceActivate())
            {
                crush(getOwner());
                crush(getOpponent());
            }
        }
    }
}

