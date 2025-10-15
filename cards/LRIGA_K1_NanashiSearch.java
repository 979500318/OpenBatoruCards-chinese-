package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;
import java.util.Objects;

public final class LRIGA_K1_NanashiSearch extends Card {

    public LRIGA_K1_NanashiSearch()
    {
        setImageSets("WXDi-P07-035");

        setOriginalName("ナナシ・探索");
        setAltNames("ナナシタンサク Nanashi Tansaku");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュから#Gを持たないそれぞれレベルの異なるシグニ２枚を対象とし、それらを手札に加える。\n" +
                "@E %X：対戦相手のすべてのシグニゾーンに【ウィルス】を１つずつ置く。"
        );

        setName("en", "Nanashi Search");
        setDescription("en",
                "@E: Put the top two cards of your deck into your trash. Then, add two target SIGNI with different levels and without a #G to your hand.\n" +
                "@E %X: Put a [[Virus]] in each of your opponent's SIGNI Zones."
        );
        
        setName("en_fan", "Nanashi Search");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck into the trash. Then, target 2 SIGNI with different levels and without #G @[Guard]@ from your trash, and add them to your hand.\n" +
                "@E %X: Put 1 [[Virus]] on all of your opponent's SIGNI zones."
        );

		setName("zh_simplified", "无名·探索");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把不持有#G的等级不同的精灵2张作为对象，将这些加入手牌。\n" +
                "@E %X:对战对手的全部的精灵区各放置[[病毒]]1个。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
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
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            millDeck(2);

            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash();
            if(filter.getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().limit(2).count() >= 2 ||
               filter.getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getDataByReferenceValues().size()).filter(size -> size > 0).limit(2).count() >= 2)
            {
                DataTable<CardIndex> data = playerTargetCard(2, filter, this::onEnterEffTargetCond);
                addToHand(data);
            }
        }
        private boolean onEnterEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 2 &&
                    ((listPickedCards.get(0).getIndexedInstance().getLevel().getValue() != listPickedCards.get(1).getIndexedInstance().getLevel().getValue()) ||
                     (!listPickedCards.get(0).getIndexedInstance().getLevel().getDataByReferenceValues().isEmpty() && !listPickedCards.get(1).getIndexedInstance().getLevel().getDataByReferenceValues().isEmpty()));
        }

        private void onEnterEff2()
        {
            forEachSIGNIZone(getOpponent(), fieldZone -> attachZoneObject(fieldZone, CardUnderType.ZONE_VIRUS));
        }
    }
}

