package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

import java.util.List;
import java.util.stream.Stream;

public final class LRIG_K3_UmrTreWielderOfTheKeyOfAdventure extends Card {

    public LRIG_K3_UmrTreWielderOfTheKeyOfAdventure()
    {
        setImageSets("WX25-P1-030", "WX25-P1-030U", "SPDi44-16");

        setOriginalName("冒険の鍵主　ウムル＝トレ");
        setAltNames("ボウケンノカギヌシウムルトレ Bouken no Kaginushi Umuru Tore");
        setDescription("jp",
                "@A $T1 %K：対戦相手のシグニ１体を対象とし、あなたのトラッシュからそれぞれレベルの異なる＜古代兵器＞のシグニ３枚を好きな順番でデッキの一番下に置く。そうした場合、それをバニッシュする。\n" +
                "@A $G1 @[@|プライマル|@]@ @[シグニを３体まで場からトラッシュに置く]@：あなたのトラッシュからこの方法でトラッシュに置いたシグニ１体につきシグニ１枚を対象とし、それらを場に出す。"
        );

        setName("en", "Umr-Tre, Wielder of The Key of Adventure");
        setDescription("en",
                "@A $T1 %K: Target 1 of your opponent's SIGNI, and put 3 <<Ancient Weapon>> SIGNI with different levels from your trash on the bottom of your deck in any order. If you do, banish it.\n" +
                "@A $G1 @[@|Primal|@]@ @[Put up to 3 SIGNI from the field into the trash]@: For each SIGNI put into the trash this way, target 1 SIGNI from your trash and put it onto the field."
        );

		setName("zh_simplified", "冒险的键主 乌姆尔=TRE");
        setDescription("zh_simplified", 
                "@A $T1 %K:对战对手的精灵1只作为对象，从你的废弃区把等级不同的<<古代兵器>>精灵3张任意顺序放置到牌组最下面。这样做的场合，将其破坏。\n" +
                "@A $G1 原始精灵3只最多从场上放置到废弃区:从你的废弃区依据这个方法放置到废弃区的精灵的数量，每有1只就把精灵1张作为对象，将这些出场。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new TrashCost(0,3, new TargetFilter().SIGNI()), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Primal");
        }

        private ConditionState onActionEff1Cond()
        {
            return canConditionBeFulfilled(new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromTrash().getExportedData().stream()) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();

            if(target != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromTrash();
                if(canConditionBeFulfilled(filter.getExportedData().stream()))
                {
                    DataTable<CardIndex> data = playerTargetCard(3, filter, this::onActionEff1TargetCond);

                    if(returnToDeck(data, DeckPosition.BOTTOM) == 3)
                    {
                        banish(target);
                    }
                }
            }
        }
        private boolean onActionEff1TargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && canConditionBeFulfilled(listPickedCards.stream());
        }
        private boolean canConditionBeFulfilled(Stream<? super CardIndex> stream)
        {
            return stream.mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().count() == 3;
        }
        
        private void onActionEff2()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                int count = getAbility().getCostPaidData().size() - (int)getAbility().getCostPaidData().stream().filter(cardIndexSnapshot -> (((CardIndexSnapshot)cardIndexSnapshot).getSourceCardIndex().getLocation() != CardLocation.TRASH)).count();
                
                DataTable<CardIndex> data = playerTargetCard(count, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable());
                putOnField(data);
            }
        }
    }
}

