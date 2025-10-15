package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_MamaCleanup extends Card {

    public LRIGA_G2_MamaCleanup()
    {
        setImageSets("WXDi-P14-033");

        setOriginalName("ママ♥お片付け");
        setAltNames("ママオカタヅケ Mama Okatazuke");
        setDescription("jp",
                "@E：あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルし、デッキの一番上のカードをライフクロスに加える。"
        );

        setName("en", "Mama ♥ Tidying Up");
        setDescription("en",
                "@E: Shuffle all cards in your trash into your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "Mama♥Cleanup");
        setDescription("en_fan",
                "@E: Shuffle all cards from your trash into your deck, and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "妈妈♥整理");
        setDescription("zh_simplified", 
                "@E :你的废弃区的全部的牌加入牌组洗切，牌组最上面的牌加入生命护甲。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(4));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private int number;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();
            
            addToLifeCloth(1);
        }
    }
}
