package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
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
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.MillCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class LRIG_K3_HanareDarkOneOfGrasping extends Card {

    public LRIG_K3_HanareDarkOneOfGrasping()
    {
        setImageSets("WXDi-P10-009", "WXDi-P10-009U");

        setOriginalName("掌握の冥者　ハナレ");
        setAltNames("ショウアクノメイジャハナレ Shouaku no Meija Hanare");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、このターン、あなたの効果によってそれのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。\n" +
                "@A $T1 @[デッキの一番上のカードをトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこの方法でトラッシュに置かれたシグニのレベル１につき－2000する。\n" +
                "@A $G1 %K0：このターン、パワーが０以下の対戦相手のシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。"
        );

        setName("en", "Hanare, Mourner of Seizure");
        setDescription("en",
                "@E: If your effect would -- the power of target SIGNI on your opponent's field this turn, the amount of power that would be -- is doubled instead.\n" +
                "@A $T1 @[Put the top card of your deck into your trash]@: Target SIGNI on your opponent's field gets --2000 power for each level of the SIGNI put into the trash this way until end of turn.\n" +
                "@A $G1 %K0: If a SIGNI on your opponent's field with power 0 or less is vanished this turn, it is put into the trash instead of the Ener Zone."
        );
        
        setName("en_fan", "Hanare, Dark One of Grasping");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI. Until end of turn, if the power of that SIGNI would be -- (minus) by your effect, it gets -- (minus) by twice as much instead.\n" +
                "@A $T1 @[Put the top card of your deck into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power for each level of the card put into the trash this way.\n" +
                "@A $G1 %K0: This turn, if your opponent's SIGNI with power 0 or less would be banished, it is put into the trash instead of the ener zone."
        );

		setName("zh_simplified", "掌握的冥者 离");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，这个回合，因为你的效果将其的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "@A $T1 牌组最上面的牌放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这个方法放置到废弃区的精灵的等级的数量，每有1级就-2000。\n" +
                "@A $G1 %K0:这个回合，力量在0以下的对战对手的精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANARE);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new MillCost(1), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();

            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(target),
                    new ModifiableAddedValueModifier<>(this::onAttachedConstEffModGetSample, this::onAttachedConstEffModAddedValue)
                );
                GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, "double_minus"));
                attachedConst.setForcedModifierUpdate(mod -> mod instanceof PowerModifier);
                
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onAttachedConstEffModAddedValue(ModifiableDouble mod, double addedValue)
        {
            return addedValue < 0 && isOwnCard(mod.getSourceAbility().getSourceCardIndex()) ? addedValue * 2 : addedValue;
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && !getAbility().getCostPaidData(0).isEmpty() && getAbility().getCostPaidData(0) != null)
            {
                gainPower(target, -2000 * getLevelByRef((CardIndexSnapshot)getAbility().getCostPaidData(0).get()), ChronoDuration.turnEnd());
            }
        }

        private void onActionEff2()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
                )
            );
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return cardIndex.getIndexedInstance().getPower().getValue() <= 0;
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}
