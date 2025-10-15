package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SPELL_W_FestivalAwake extends Card {

    public SPELL_W_FestivalAwake()
    {
        setImageSets("WXDi-P14-053");
        setLinkedImageSets("WXDi-P14-041");

        setOriginalName("フェスティバル・アウェイク");
        setAltNames("フェスティバルアウェイク Fesutibaru Aueiku");
        setDescription("jp",
                "あなたの白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋2000し、それは@>@C：対戦相手のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。@@を得る。それが《幻怪姫　ドーナ//フェゾーネ》の場合、それは覚醒する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Festival Awake");
        setDescription("en",
                "Target white SIGNI on your field gets +2000 power and gains@>@C: If a SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put into the trash instead of the Ener Zone.@@until the end of your opponent's next end phase. If it is a \"Dona//Fesonne, Phantom Spirit Queen\", awaken it." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Festival Awake");
        setDescription("en_fan",
                "Target 1 of your white SIGNI, and until the end of your opponent's next turn, it gets +2000 power, and it gains:" +
                "@>@C: If this SIGNI would banish your opponent's SIGNI in battle, it is put into the trash instead of the ener zone.@@" +
                "If that SIGNI is \"Dona//Fessone, Natural Apparition Princess\", it awakens." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "节日·苏醒");
        setDescription("zh_simplified", 
                "你的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000，其得到\n" +
                "@>@C :对战对手的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。@@\n" +
                "。其是《幻怪姫　ドーナ//フェゾーネ》的场合，将其觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.WHITE)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                gainPower(spell.getTarget(), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
                
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
                ));
                attachAbility(spell.getTarget(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
                
                if(spell.getTarget() != null && spell.getTarget().getIndexedInstance().getName().getValue().contains("幻怪姫　ドーナ//フェゾーネ"))
                {
                    spell.getTarget().getIndexedInstance().getCardStateFlags().addValue(CardStateFlag.AWAKENED);
                }
            }
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && !isOwnCard(event.getCallerCardIndex());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
