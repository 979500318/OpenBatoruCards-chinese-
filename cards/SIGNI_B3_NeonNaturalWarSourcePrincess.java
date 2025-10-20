package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.ActionBanish;
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
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B3_NeonNaturalWarSourcePrincess extends Card {

    public SIGNI_B3_NeonNaturalWarSourcePrincess()
    {
        setImageSets("WX25-P1-056", "WX25-P1-056U");
        setLinkedImageSets("WX25-P1-022");

        setOriginalName("羅原闘姫　Ne");
        setAltNames("ラゲントウキネオン Ragentouki Neon");
        setDescription("jp",
                "@C：あなたの＜原子＞のシグニが対戦相手の効果によって場を離れる場合、その移動がバニッシュによるものでないなら、代わりにそのシグニをバニッシュしてもよい。\n" +
                "@A $T1 %B @[アップ状態の＜原子＞のシグニ２体をダウンする]@：あなたの場に《ミルルン・ケミストリー》がいる場合、ターン終了時まで、このシグニは@>@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );

        setName("en", "Neon, Natural War Source Princess");
        setDescription("en",
                "@C: If your <<Atom>> SIGNI would leave the field by your opponent's effect and that movement isn't caused by banish, you may banish that SIGNI instead.\n" +
                "@A $T1 %B @[Down 2 upped <<Atom>> SIGNI]@: If your LRIG is \"Milulun Chemistry\", until end of turn, this SIGNI gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "罗原斗姬 Ne");
        setDescription("zh_simplified", 
                "@C :你的<<原子>>精灵因为对战对手的效果离场的场合，假如那次移动不是因为破坏，作为替代，可以把那只精灵破坏。\n" +
                "@A $T1 %B竖直状态的<<原子>>精灵2只横置:你的场上有《ミルルン・ケミストリー》的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ATOM), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data -> {
                if(data.getGenericData(0) instanceof ActionBanish) return null;
                return new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideCond, this::onConstEffModOverrideHandler);
            }));
            
            ActionAbility act = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLUE, 1)),
                new DownCost(2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ATOM))
            ), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSourceCardIndex()) &&
                   !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionBanish(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onActionEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ミルルン・ケミストリー"))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
