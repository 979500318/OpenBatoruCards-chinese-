package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.ActionEnerPay.PaidEnerData;
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
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.FieldConst;
import open.batoru.game.gfx.GFXFieldBackground;
import open.batoru.game.gfx.GFXZoneBackground;

public final class LRIG_W3_YukayukaThreeThreeKiraKira extends Card {

    public LRIG_W3_YukayukaThreeThreeKiraKira()
    {
        setImageSets("SPDi43-01", "SPDi43-01P");

        setOriginalName("ゆかゆか☆さんさんきらきら");
        setAltNames("ユカユカサンサンキラキラ Yukayuka Sansan Kirakira");
        setDescription("jp",
                "@U：あなたのターン終了時、対戦相手は%X %Xを支払わないかぎり自分のシグニ２体を選びトラッシュに置く。\n" +
                "@A $G1 @[@|永遠♡不滅☆宣言|@]@ %W0：次の対戦相手のターン終了時まで、このルリグは@>@C：対戦相手のシグニの@U能力が発動する場合、対戦相手が%Xを支払わないかぎり、その能力は何もしない。@@を得る。"
        );

        setName("en", "Yukayuka☆Three Three Kira Kira");
        setDescription("en",
                "@U: At the end of your turn, your opponent chooses 2 of their SIGNI and puts them into the trash unless they pay %X %X.\n" +
                "@A $G1 @[@|Eternal♡Immortal☆Declaration|@]@ %W0: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: If the @U ability of your opponent's SIGNI would activate, that ability does nothing unless your opponent pays %X."
        );

		setName("zh_simplified", "由香香☆三重混响");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，对战对手如果不把%X %X:支付，那么选自己的精灵2只放置到废弃区。（1只的场合，将其选）\n" +
                "@A $G1 永远♡不灭☆宣言%W0:直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C 对战对手的精灵的@U能力发动的场合，如果对战对手不把%X:支付，那么那个能力不能做任何事。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("EternalImmortalDeclaration");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getSIGNICount(getOpponent()) > 0 && !payEner(getOpponent(), Cost.colorless(2)))
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(2,getSIGNICount(getOpponent())), new TargetFilter(TargetHint.TRASH).own().SIGNI());
                trash(data);
            }
        }

        private void onActionEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.ABILITY, OverrideScope.GLOBAL,OverrideFlag.MANDATORY, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
            ));
            GFXZoneBackground.attachToAbility(attachedConst, new GFXFieldBackground(getOpponent(), "yukayuka_ghosts", 660,1250, -FieldConst.FIELD_CARD_WIDTH*2,FieldConst.FIELD_CARD_HEIGHT).withCenterOffset());
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() instanceof AutoAbility && !isOwnCard(event.getSource()) && CardType.isSIGNI(event.getSource().getCardReference().getType());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addEnerPayAction(getOpponent(), Cost.colorless(1)).setOnActionCompleted(() -> {
                if(!list.getAction(0).isSuccessful()) ((GameAction<PaidEnerData>)list.getAction(0)).getDataTable().add(new PaidEnerData(null,null));
                else list.getAction(0).getDataTable().clear();
            });
        }
    }
}

