package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.game.gfx.GFXZoneCutout;
import open.batoru.game.gfx.GFXFieldBackground;

public final class ARTS_K_ScissorHands extends Card {

    public ARTS_K_ScissorHands()
    {
        setImageSets("WX25-P2-009", "WX25-P2-009U");

        setOriginalName("シザー・ハンズ");
        setAltNames("シザーハンズ Shizaa Hanzu");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@U $G1：対戦相手のライフクロスが０枚になったとき、次に対戦相手が行うリフレッシュは「自分のトラッシュにあるすべてのカードをデッキに加えてシャッフルし、ルリグデッキからカード１枚をルリグトラッシュに置く。」になる。\n" +
                "@U $TO $T1：あなたか対戦相手のデッキからカードが１枚以上トラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Scissor Hands");
        setDescription("en",
                "During this game, you gain the following abilities:" +
                "@>@U $G1: When the number of cards in your opponent's life cloth becomes 0, the next refresh your opponent performs becomes: \"Shuffle all cards from your trash into your deck, and put 1 card from your LRIG deck into the LRIG trash.\".\n" +
                "@U $TO $T1: When 1 or more cards are put from your or your opponent's deck into the trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

        setName("zh_simplified", "长戟·铡刃");
        setDescription("zh_simplified",
                "这场游戏期间，你得到以下的能力。" +
                "@>@U $G1: 当对战对手的生命护甲变为0张时，下一次对战对手进行的重构变为「自己的废弃区的全部的牌加入牌组洗切，从分身牌组把1张牌放置到分身废弃区。」。\n" +
                "@U $TO $T1: 当从你或对战对手的牌组把牌1张以上放置到废弃区时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            AutoAbility attachedAuto1 = new AutoAbility(GameEventId.MOVE, this::onAttachedAutoEff1);
            attachedAuto1.setCondition(this::onAttachedAutoEff1Cond);
            attachedAuto1.setUseLimit(UseLimit.GAME, 1);
            attachPlayerAbility(getOwner(), attachedAuto1, ChronoDuration.permanent());

            AutoAbility attachedAuto2 = new AutoAbility(GameEventId.TRASH, this::onAttachedAutoEff2);
            attachedAuto2.setCondition(this::onAttachedAutoEff2Cond);
            attachedAuto2.setUseLimit(UseLimit.TURN, 1);
            attachedAuto2.setNestedDescriptionOffset(1);
            GFXFieldBackground.attachToAbility(attachedAuto2, new GFXFieldBackground(getOwner(), CardLocation.LRIG, "aura_insects"));
            attachPlayerAbility(getOwner(), attachedAuto2, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEff1Cond(CardIndex caller)
        {
            boolean isCrush = EventMove.getDataMoveLocation() == CardLocation.CHECK_ZONE;
            return !isOwnCard(caller) && (!isCrush ? caller.isEffectivelyAtLocation(CardLocation.LIFE_CLOTH) : (caller.getLocation() == CardLocation.CHECK_ZONE && caller.getOldLocation() == CardLocation.LIFE_CLOTH)) &&
                    EventMove.getDataMoveLocation() != CardLocation.REVEALED && EventMove.getDataMoveLocation() != CardLocation.LOOKED &&
                    EventMove.getDataMoveLocation() != CardLocation.LIFE_CLOTH &&
                    getLifeClothCount(getOpponent()) == (!isCrush ? 1 : 0) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff1(CardIndex caller)
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.permanent());
            record.setAlias("refresh"+getOwner().getId());
            GFXZoneCutout.attachToChronoRecord(record, new GFXZoneCutout(getOpponent(), CardLocation.DECK_LRIG, "cutout"));
            addPlayerRuleCheck(PlayerRuleCheckType.ACTION_OVERRIDE, getOpponent(), record, data ->
                new OverrideAction(GameEventId.REFRESH, OverrideScope.GLOBAL, OverrideFlag.MANDATORY, this::onAttachedAutoEffRuleCheckOverrideHandler)
            );
        }
        private void onAttachedAutoEffRuleCheckOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addTargetAction(getOpponent(), new TargetFilter(TargetHint.TRASH).own().fromLocation(CardLocation.DECK_LRIG).anyCard()).setOnActionCompleted(() -> {
                if(list.getAction(0).isSuccessful()) ((ActionTrash)list.getAction(1)).setCardIndex((CardIndex)list.getAction(0).getDataTable().get(0));
            });
            list.addAction(new ActionTrash());
            Game.getCurrentGame().getChronoScheduler().eraseChronoRecords(null, "refresh"+getOwner().getId());
        }
        private ConditionState onAttachedAutoEff2Cond(CardIndex caller)
        {
            return isOwnTurn() && getEvent().isAtOnce(1) && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}
