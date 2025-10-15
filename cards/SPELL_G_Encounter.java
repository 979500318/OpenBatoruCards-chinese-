package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SPELL_G_Encounter extends Card {
    
    public SPELL_G_Encounter()
    {
        setImageSets("WXDi-P06-077");
        
        setOriginalName("遭遇");
        setAltNames("ソウグウ Souguu");
        setDescription("jp",
                "あなたの緑のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@U：あなたのメインフェイズ以外でこのシグニがバニッシュされたとき、【エナチャージ２】をする。@@を得る。\n\n" +
                "@A %G0：あなたのエナゾーンからこのカードを手札に加える。この能力はあなたの場に＜美巧＞のシグニがある場合にしか使用できない。"
        );
        
        setName("en", "Encounter");
        setDescription("en",
                "Target green SIGNI on your field gains@>@U: When this SIGNI is vanished outside of your main phase, [[Ener Charge 2]].@@until the end of your opponent's next end phase.\n\n" +
                "@A %G0: Add this card from your Ener Zone to your hand. This ability can only be used if there is a <<Beauty>> SIGNI on your field.  "
        );
        
        setName("en_fan", "Encounter");
        setDescription("en_fan",
                "Target 1 green SIGNI on your field, and until the end of your opponent's next turn, it gains:\n" +
                "@>@U: When this SIGNI is banished other than during your main phase, [[Ener Charge 2]].@@\n" +
                "@A %G0: Add this card from your ener zone to your hand. This ability can only be used if there is a <<Beautiful Technique>> SIGNI on your field."
        );
        
		setName("zh_simplified", "遭遇");
        setDescription("zh_simplified", 
                "你的绿色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@U :当在你的主要阶段以外把这只精灵被破坏时，[[能量填充2]]。@@\n" +
                "@A %G0:从你的能量区把这张牌加入手牌。这个能力只有在你的场上有<<美巧>>精灵的场合才能使用。（这个能力只有在这张魔法在能量区的场合才能使用）\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setActiveLocation(CardLocation.ENER);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN)));
        }
        private void onSpellEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !caller.getIndexedInstance().isOwnTurn() || getCurrentPhase() != GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(2);
        }
        
        private ConditionState onActionEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            addToHand(getCardIndex());
        }
    }
}
