package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardUnderType;
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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_K3_HerakabutoPhantomInsectPrincess extends Card {

    public SIGNI_K3_HerakabutoPhantomInsectPrincess()
    {
        setImageSets("WX25-P2-060", "WX25-P2-060U");
        setLinkedImageSets("WX25-P2-030");

        setOriginalName("幻蟲姫　ヘラカブト");
        setAltNames("ゲンチュウキヘラカブト Genchuuki Herakabuto");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《ミュウ＝パピヨン》がいる場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。それに【チャーム】が付いている場合、代わりにターン終了時まで、それのパワーを－10000する。\n" +
                "@A $T1 @[エナゾーンから＜凶蟲＞のシグニ１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、このターン、それがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。"
        );

        setName("en", "Herakabuto, Phantom Insect Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Myu-Papillon\", target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. If it has a [[Charm]] attached ot it, until end of turn, it gets --10000 power instead.\n" +
                "@A $T1 @[Put 1 <<Misfortune Insect>> SIGNI from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and this turn, if it would be banished, it is put into the trash instead of the ener zone."
        );

		setName("zh_simplified", "幻虫姬 长戟大兜虫");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《ミュウ＝パピヨン》的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。其有[[魅饰]]附加的场合，作为替代，直到回合结束时为止，其的力量-10000。\n" +
                "@A $T1 从能量区把<<凶蟲>>精灵1张放置到废弃区:对战对手的精灵1只作为对象，这个回合，其被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
        setPower(10000);

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

            ActionAbility act = registerActionAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT).fromEner()), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ミュウ＝パピヨン"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null)
                {
                    gainPower(target, target.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_CHARM) == 0 ? -5000 : -10000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(target), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
                ));
                GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/trash", 0.75,3)));
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}
