package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.game.FieldZone;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class SIGNI_W3_ExiaHolyAngelPrincess extends Card {

    public SIGNI_W3_ExiaHolyAngelPrincess()
    {
        setImageSets("WXDi-P04-032", "SPDi10-05", "WX25-P1-117");

        setOriginalName("聖天姫　エクシア");
        setAltNames("セイテンキエクシア　Seitenki Ekushia");
        setDescription("jp",
                "@U $T1：あなたのシグニが対戦相手の、能力か効果の対象になったとき、対戦相手のシグニゾーン１つを指定する。このターン、対戦相手はそのシグニゾーンにあるシグニでアタックできない。\n" +
                "@U：あなたのターン終了時、あなたのすべてのシグニをアップする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Exia, Blessed Angel Queen");
        setDescription("en",
                "@U $T1: When a SIGNI on your field becomes the target of your opponent's ability or effect, choose one of your opponent's SIGNI Zones. Your opponent cannot attack with SIGNI in that SIGNI Zone this turn.\n" +
                "@U: At the end of your turn, up all SIGNI on your field." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Exia, Holy Angel Princess");
        setDescription("en_fan",
                "@U $T1: When your SIGNI is targeted by your opponent's ability or effect, choose 1 of your opponent's SIGNI zones. This turn, your opponent can't attack with SIGNI in that SIGNI zone.\n" +
                "@U: At the end of your turn, up all of your SIGNI." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "圣天姬 艾克希亚");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵被作为对战对手的，能力或效果的对象时，对战对手的精灵区1个指定。这个回合，对战对手的那个精灵区的精灵不能攻击。\n" +
                "@U :你的回合结束时，你的全部的精灵竖直。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.TARGET, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    isOwnCard(caller) && CardLocation.isSIGNI(caller.getLocation()) &&
                    EventTarget.getDataSourceTargetRole() != caller.getIndexedInstance().getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(isOwnTurn()) return;
            FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();

            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            GFX.attachToChronoRecord(record, new GFXZoneUnderIndicator(getOpponent(),fieldZone.getZoneLocation(), "chain"));
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_ATTACK, getOpponent(), record, data ->
                data.getSourceCardIndex().getLocation() == fieldZone.getZoneLocation() ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            up(getSIGNIOnField(getOwner()));
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
