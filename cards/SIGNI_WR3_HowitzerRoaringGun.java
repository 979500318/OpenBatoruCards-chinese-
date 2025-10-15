package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
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
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_WR3_HowitzerRoaringGun extends Card {

    public SIGNI_WR3_HowitzerRoaringGun()
    {
        setImageSets("WX24-P4-050");

        setOriginalName("轟砲　ハウザー");
        setAltNames("ゴウホウハウザー Gouhou Hauzaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー8000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。\n" +
                "@A #D：このターン、次にこのシグニの効果によって対戦相手のシグニ１体がバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。"
        );

        setName("en", "Howitzer, Roaring Gun");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 8000 or less, and you may discard 1 card from your hand. If you do, banish it.\n" +
                "@A #D: This turn, the next time 1 of your opponent's SIGNI would be banished by the effect of this SIGNI, it is put into the trash instead of the ener zone."
        );

		setName("zh_simplified", "轰炮 榴弹炮");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量8000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n" +
                "@A #D:这个回合，下一次因为这只精灵的效果把对战对手的精灵1只破坏的场合，放置到能量区，作为替代，放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }

        private void onActionEff()
        {
            ChronoRecord record = new ChronoRecord(getCardIndex(), ChronoDuration.turnEnd());
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI().match(getCardIndex()),
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler)
                )
            );
            attachPlayerAbility(getOwner(), attachedConst, record);
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getCallerCardIndex());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
            CardAbilities.removePlayerAbility(sourceAbilityRC);
        }
    }
}
