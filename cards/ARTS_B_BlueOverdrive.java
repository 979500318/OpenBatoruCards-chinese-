package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.EventDamage;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_B_BlueOverdrive extends Card {

    public ARTS_B_BlueOverdrive()
    {
        setImageSets("WX24-P3-005", "WX24-P3-005U");

        setOriginalName("ブルー・オーバードライブ");
        setAltNames("ブルーオーバードライブ Buruu Oobaadoraibu");
        setDescription("jp",
                "あなたのレベル２以上のセンタールリグ１体を対象とし、次のあなたのエナフェイズ終了時まで、それのリミットを＋１し、それは以下の能力を得る。" +
                "@>@C：あなたがダメージを受ける場合、代わりに手札を１枚捨ててもよい。そうした場合、このルリグはこの能力を失う。\n" +
                "@A $T1 #D：カードを４枚引き、手札を２枚捨てる。"
        );

        setName("en", "Blue Overdrive");
        setDescription("en",
                "Target your level 2 or higher center LRIG, and until the end of your next ener phase, it gets +1 limit, and it gains:" +
                "@>@C: If you would be damaged, you may discard 1 card from your hand instead. If you do, this LRIG loses this ability.\n" +
                "@A $T1 #D: Draw 4 cards, and discard 2 cards from your hand."
        );

		setName("zh_simplified", "蔚蓝·超速传动");
        setDescription("zh_simplified", 
                "你的等级2以上的核心分身1只作为对象，直到下一个你的充能阶段结束时为止，其的界限+1，其得到以下的能力。\n" +
                "@>@C :你受到伤害的场合，作为替代，可以把手牌1张舍弃。这样做的场合，这只分身的这个能力失去。\n" +
                "@A $T1 :横置抽4张牌，手牌2张舍弃。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }

        private ConditionState onARTSEffCond()
        {
            return new TargetFilter().own().LRIG().withLevel(2,0).getValidTargetsCount() == 0 ? ConditionState.WARN : ConditionState.OK;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(2,0)).get();
            if(target == null) return;
            
            gainValue(target, target.getIndexedInstance().getLimit(),1d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler))
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);
            GFXZoneWall.attachToAbility(attachedConst, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "generic", new int[]{50,150,205}));
            attachAbility(target, attachedConst, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ActionAbility attachedAct = new ActionAbility(new DownCost(), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachAbility(target, attachedAct, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getHandCount(getOwner()) >= 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventDamage)event).getPlayer() == sourceAbilityRC.getAbilityOwner();
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addDiscardAction(1).setOnActionCompleted(() -> {
                if(list.getAction(0).isSuccessful()) sourceAbilityRC.disable();
            });
        }
        private void onAttachedActionEff()
        {
            draw(4);
            discard(2);
        }
    }
}
