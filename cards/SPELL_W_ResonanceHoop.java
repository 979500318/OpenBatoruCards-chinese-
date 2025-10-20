package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionDown;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SPELL_W_ResonanceHoop extends Card {

    public SPELL_W_ResonanceHoop()
    {
        setImageSets("WX25-P2-071");

        setOriginalName("レゾナンス・フープ");
        setAltNames("レゾナンスフープ Rezonansu Huupu");
        setDescription("jp",
                "あなたの＜宇宙＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋5000する。それがレゾナの場合、次の対戦相手のターン終了時まで、それは@>@C：このシグニが対戦相手の効果によって場を離れる場合、代わりにこの能力を失う。そうした場合、このシグニをダウンする。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Resonance Hoop");
        setDescription("en",
                "Target 1 of your <<Space>> SIGNI, and until the end of your opponent's next turn, it gets +5000 power. If it is a Resona, until the end of your opponent's next turn, it gains:" +
                "@>@C: If this SIGNI would leave the field by your opponent's effect, it loses this ability instead. If it does, down this SIGNI.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "共鸣回响·指轮");
        setDescription("zh_simplified", 
                "你的<<宇宙>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+5000。其是共鸣的场合，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C :这只精灵因为对战对手的效果离场的场合，作为替代，这个能力失去。这样做的场合，这只精灵横置。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.SPACE)));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(target != null)
            {
                gainPower(target, 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                
                if(target.getIndexedInstance().getTypeByRef() == CardType.RESONA)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                        new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler)
                    ));
                    GFXCardTextureLayer.attachToAbility(attachedConst, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/guard", 0.75,3)));
                    attachAbility(target, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !sourceAbilityRC.getSourceCardIndex().getIndexedInstance().isOwnCard(event.getSource()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            sourceAbilityRC.disable();
            list.addNonMandatoryAction(new ActionDown(sourceAbilityRC.getSourceCardIndex()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
