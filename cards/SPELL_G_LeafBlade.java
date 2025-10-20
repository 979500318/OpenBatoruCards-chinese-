package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_LeafBlade extends Card {

    public SPELL_G_LeafBlade()
    {
        setImageSets("WX24-P2-087");

        setOriginalName("葉刃");
        setAltNames("ヨウジン Youjin");
        setDescription("jp",
                "あなたの＜植物＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：あなたのアタックフェイズ開始時、パワーがこのシグニのパワー以下の対戦相手のシグニ１体を対象とし、アップ状態のこのシグニをダウンしてもよい。そうした場合、それをエナゾーンに置く。@@を得る。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Leaf Blade");
        setDescription("en",
                "Target 1 of your <<Plant>> SIGNI, and until end of turn, it gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power equal to or less than this SIGNI's, and you may down this upped SIGNI. If you do, put it into the ener zone.@@" +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "叶刃");
        setDescription("zh_simplified", 
                "你的<<植物>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :你的攻击阶段开始时，力量在这只精灵的力量以下的对战对手的精灵1只作为对象，可以把竖直状态的这只精灵横置。这样做的场合，将其放置到能量区。@@" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.PLANT)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex cardIndex)
        {
            CardIndex source = getAbility().getSourceCardIndex();
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,source.getIndexedInstance().getPower().getValue())).get();
            if(target != null && !source.getIndexedInstance().isState(CardStateFlag.DOWNED) &&
               source.getIndexedInstance().playerChoiceActivate() && source.getIndexedInstance().down(source))
            {
                source.getIndexedInstance().putInEner(target);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();

            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
