package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
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
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityCross;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class LRIG_R3_ThreeOfTamayorihimeStrangelyUnitedFlames extends Card {

    public LRIG_R3_ThreeOfTamayorihimeStrangelyUnitedFlames()
    {
        setImageSets("WX25-P1-018", "WX25-P1-018U", "SPDi44-08");

        setOriginalName("合炎奇炎　タマヨリヒメ之参");
        setAltNames("ゴウエン キエンタマヨリヒメ ノ サン Gouen Kien Tamayorihime no San");
        setDescription("jp",
                "@U $T1：あなたのメインフェイズの間、あなたの＜ウェポン＞のシグニ１体が場に出たとき、あなたのトラッシュら=Xを持つ＜ウェポン＞のシグニを１枚まで対象とし、それを場に出す。\n" +
                "@A $G1 @[@|イノセンス|@]@ %R0：次の対戦相手のターン終了時まで、あなたのクロス状態のすべてのシグニの基本パワーを15000にし、このルリグは@>@C：あなたのクロス状態のシグニ１体が対戦相手の効果によって場を離れる場合、代わりにこのルリグはこの能力を失う。@@を得る。"
        );

        setName("en", "Three of Tamayorihime, Strangely United Flames");
        setDescription("en",
                "@U $T1: During your main phase, when 1 of your <<Weapon>> SIGNI enters the field, target up to 1 =X <<Weapon>> SIGNI from your trash, and put it onto the field.\n" +
                "@A $G1 @[@|Innocence|@]@ %R0: Until the end of your opponent's next turn, all of your crossed SIGNI's base power becomes 15000, and this LRIG gains:" +
                "@>@C: If 1 of your crossed SIGNI would leave the field by your opponent's effect, this LRIG loses this ability instead."
        );

		setName("zh_simplified", "合炎奇炎 玉依姬之叁");
        setDescription("zh_simplified", 
                "@U $T1 :你的主要阶段期间，当你的<<ウェポン>>精灵1只出场时，从你的废弃区把持有[[交错]]的<<ウェポン>>精灵1张最多作为对象，将其出场。\n" +
                "@A $G1 圣洁%R0:直到下一个对战对手的回合结束时为止，你的交错状态的全部的精灵的基本力量变为15000，这只分身得到\n" +
                "@>@C :你的交错状态的精灵1只因为对战对手的效果离场的场合，作为替代，这只分身的这个能力失去。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Innocence");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getCurrentPhase() == GamePhase.MAIN && isOwnTurn() &&
                   CardType.isSIGNI(caller.getCardReference().getType()) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.WEAPON) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.WEAPON).withStockAbility(StockAbilityCross.class).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onActionEff()
        {
            setBasePower(new TargetFilter().own().SIGNI().crossed().getExportedData(), 15000, ChronoDuration.nextTurnEnd(getOpponent()));
            
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI().crossed(), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.MANDATORY, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler))
            );
            GFX.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, new GFXTextureCardCanvas("border/guard", 0.75,3)));
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSourceCardIndex()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            sourceAbilityRC.disable();
        }
    }
}

