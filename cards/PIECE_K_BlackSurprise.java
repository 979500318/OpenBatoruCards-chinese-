package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class PIECE_K_BlackSurprise extends Card {

    public PIECE_K_BlackSurprise()
    {
        setImageSets("WXDi-P16-007");

        setOriginalName("ブラック・サプライズ");
        setAltNames("ブラックサプライズ Burakku Sapuraizu");
        setDescription("jp",
                "=U あなたの場に黒のルリグがいる\n\n" +
                "あなたの場に黒のルリグが２体以上いるかぎり、このピースの使用コストはあなたの場にいる黒のルリグ１体につき%K減る。\n\n" +
                "このターン、あなたの効果によって対戦相手のシグニのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。\n" +
                "このターン、対戦相手のシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。"
        );

        setName("en", "Black Surprise");
        setDescription("en",
                "=U You have a black LRIG on your field.\n\nAs long as there are two or more black LRIG on your field, the use cost of this PIECE is reduced by %K for each black LRIG on your field.\n\nIf your effect would -- (minus) the power of a SIGNI on your opponent's field this turn, the amount of power that would be -- (minus) is doubled instead.\nIf a SIGNI on your opponent's field is vanished this turn, it is put into the trash instead of the Ener Zone."
        );
        
        setName("en_fan", "Black Surprise");
        setDescription("en_fan",
                "=U You have a black LRIG on your field\n\n" +
                "While there are 2 or more black LRIG on your field, the use cost of this piece is reduced by %K for each black LRIG on your field.\n\n" +
                "This turn, if the power of your opponent's SIGNI would be -- (minus), it gets -- (minus) by twice as much instead.\n" +
                "This turn, if your opponent's SIGNI would be banished, it is put into the trash instead of the ener zone."
        );

		setName("zh_simplified", "漆黑·惊叹");
        setDescription("zh_simplified", 
                "=U你的场上有黑色的分身\n" +
                "你的场上的黑色的分身在2只以上时，这张和音的使用费用依据你的场上的黑色的分身的数量，每有1只就减%K。\n" +
                "这个回合，因为你的效果把对战对手的精灵的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "这个回合，对战对手的精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 3));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setCostModifier(this::onPieceEffModCostGetSample);
        }

        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private CostModifier onPieceEffModCostGetSample()
        {
            int count = new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount();
            return count >= 2 ? new CostModifier(() -> new EnerCost(Cost.color(CardColor.BLACK, count)), ModifierMode.REDUCE) : null;
        }
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst1 = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new ModifiableAddedValueModifier<>(this::onAttachedConstEffMod1GetSample, this::onAttachedConstEffMod1AddedValue)
            );
            GFXCardTextureLayer.attachToSharedAbility(attachedConst1, cardIndex -> new GFXCardTextureLayer(cardIndex, "double_minus"));
            attachedConst1.setForcedModifierUpdate(mod -> mod instanceof PowerModifier);
            attachPlayerAbility(getOwner(), attachedConst1, ChronoDuration.turnEnd());
            
            ConstantAbilityShared attachedConst2 = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffMod2OverrideHandler)
                )
            );
            attachPlayerAbility(getOwner(), attachedConst2, ChronoDuration.turnEnd());
        }
        private ModifiableDouble onAttachedConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onAttachedConstEffMod1AddedValue(ModifiableDouble mod, double addedValue)
        {
            return addedValue < 0 ? addedValue * 2 : addedValue;
        }
        private void onAttachedConstEffMod2OverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}

