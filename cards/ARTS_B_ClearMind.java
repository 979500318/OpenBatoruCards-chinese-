package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.LifeBurst;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class ARTS_B_ClearMind extends Card {

    public ARTS_B_ClearMind()
    {
        setImageSets("WX24-P1-005", "WX24-P1-005U");

        setOriginalName("クリアー・マインド");
        setAltNames("クリアーマインド Kuriaa Maindo");
        setDescription("jp",
                "以下の３つから２つまで選ぶ。\n" +
                "$$1対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "$$2対戦相手のパワー10000以下のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "$$3カードを３枚引く。"
        );

        setName("en", "Clear Mind");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIG, and freeze it.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 10000 or less, and put it on the bottom of their deck.\n" +
                "$$3 Draw 3 cards."
        );

		setName("zh_simplified", "洁净·精神");
        setDescription("zh_simplified", 
                "从以下的3种选2种最多。\n" +
                "$$1 对战对手的分身1只作为对象，将其冻结。\n" +
                "$$2 对战对手的力量10000以下的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "$$3 抽3张牌。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(0,2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
                freeze(target);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(0,10000)).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
            if((modes & 1<<2) != 0)
            {
                draw(3);
            }
        }
    }
}

