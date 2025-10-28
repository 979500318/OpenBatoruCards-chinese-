package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_K_EvilPlan extends Card {

    public ARTS_K_EvilPlan()
    {
        setImageSets("WX24-P1-028");

        setOriginalName("イビル・プラン");
        setAltNames("イビルプラン Ibiru Puran");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュからシグニを３枚まで対象とし、それらを場に出す。"
        );

        setName("en", "Evil Plan");
        setDescription("en",
                "Put the top 3 cards of your deck into the trash. Then, target up to 3 SIGNI from your trash, and put them onto the field."
        );

        setName("zh_simplified", "邪恶·计划");
        setDescription("zh_simplified", 
                "从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把精灵3张最多作为对象，将这些出场。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            millDeck(3);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable());
            putOnField(data);
        }
    }
}

