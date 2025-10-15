package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;
import open.batoru.data.CardDataImageSet.Mask;

public final class ARTS_X_VacationCharging extends Card {

    public ARTS_X_VacationCharging()
    {
        setImageSets("WX24-P2-042", Mask.IGNORE+"SPDi37-18");

        setOriginalName("バカンス・チャージング");
        setAltNames("バカンス・チャージング Bakansu Chaajingu");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中からカードを好きな枚数手札に加え、残りをエナゾーンに置く。"
        );

        setName("en", "Vacation Charging");
        setDescription("en",
                "Look at the top 3 cards of your deck. Add any number of cards from among them to your hand, and put the rest into the ener zone."
        );

		setName("zh_simplified", "假日·充能");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把牌任意张数加入手牌，剩下的放置到能量区。\n"
        );

        setType(CardType.ARTS);
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
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            putInEner(getCardsInLooked(getOwner()));
        }
    }
}

