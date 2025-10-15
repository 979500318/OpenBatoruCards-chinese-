package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventDamage;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

import java.util.List;
import java.util.stream.Stream;

public final class LRIG_W3_TawilTreProlongedOfLifeAdventurer extends Card {

    public LRIG_W3_TawilTreProlongedOfLifeAdventurer()
    {
        setImageSets("WX25-P1-014", "WX25-P1-014U","SPDi44-12");

        setOriginalName("永らえし冒険者　タウィル＝トレ");
        setAltNames("ナガラエシボウケンシャタウィルトレ Nagaraeshi Boukensha Tauiru Tore");
        setDescription("jp",
                "@A $T1 %W：対戦相手のシグニ１体を対象とし、あなたのトラッシュからそれぞれレベルの異なる＜天使＞のシグニ3枚を好きな順番でデッキの一番下に置く。そうした場合、それを手札に戻す。\n" +
                "@A $G1 @[@|プライマル|@]@ %W0：次の対戦相手のターン終了時まで、このルリグは@>@C：あなたがダメージを受ける場合、代わりに手札を１枚捨てるかあなたのエナゾーンからカード１枚トラッシュに置いてもよい。そうした場合、このルリグはこの能力を失う。@@を得る。"
        );

        setName("en", "Tawil-Tre, Prolonged of Life Adventurer");
        setDescription("en",
                "@A $T1 %W: Target 1 of your opponent's SIGNI, and put 3 <<Angel>> SIGNI with different levels from your trash on the bottom of your deck in any order. If you do, return it to their hand.\n" +
                "@A $G1 @[@|Primal|@]@ %W0: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: If you would be damaged, you may discard 1 card from your hand or put 1 card from your ener zone into the trash instead. If you do, this LRIG loses this ability."
        );

		setName("zh_simplified", "永生的冒险者 塔维尔=TRE");
        setDescription("zh_simplified", 
                "@A $T1 %W:对战对手的精灵1只作为对象，从你的废弃区把等级不同的<<天使>>精灵3张任意顺序放置到牌组最下面。这样做的场合，将其返回手牌。\n" +
                "@A $G1 原始%W0:直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :你受到伤害的场合，作为替代，可以把手牌1张舍弃或从你的能量区把1张牌放置到废弃区。这样做的场合，这只分身的这个能力失去。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Primal");
        }
        
        private ConditionState onActionEff1Cond()
        {
            return canConditionBeFulfilled(new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().getExportedData().stream()) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();

            if(target != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash();
                if(canConditionBeFulfilled(filter.getExportedData().stream()))
                {
                    DataTable<CardIndex> data = playerTargetCard(3, filter, this::onActionEff1TargetCond);
                    
                    if(returnToDeck(data, DeckPosition.BOTTOM) == 3)
                    {
                        addToHand(target);
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
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler))
            );
            GFX.attachToAbility(attachedConst, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "generic"));
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventDamage)event).getPlayer() == getOwner();
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addTargetAction(0,1, new TargetFilter().own().or(new TargetFilter().fromHand(), new TargetFilter().fromEner())).setOnActionCompleted(() -> {
                if(list.getAction(0).isSuccessful())
                {
                    ((ActionTrash)list.getAction(1)).setCardIndex((CardIndex)list.getAction(0).getDataTable().get());
                    sourceAbilityRC.disable();
                }
            });
            list.addAction(new ActionTrash());
        }
    }
}

