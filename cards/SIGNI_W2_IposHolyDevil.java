package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.game.FieldZone;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class SIGNI_W2_IposHolyDevil extends Card {

    public SIGNI_W2_IposHolyDevil()
    {
        setImageSets("WXDi-P11-055", "SPDi38-12");

        setOriginalName("聖魔　イポス");
        setAltNames("セイマイポス Seima Iposu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、正面にシグニがない場合、そのアタックを無効にしてもよい。そうした場合、シグニゾーン１つを指定する。次の対戦相手のターンの間、対戦相手はそのシグニゾーンにあるシグニでアタックできない。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Ipos, Blessed Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is no SIGNI in front of it, you may negate that attack. If you do, choose one of your opponent's SIGNI Zones. During your opponent's next turn, your opponent cannot attack with the SIGNI in that SIGNI Zone." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Ipos, Holy Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is no SIGNI in front of it, you may disable that attack. If you do, choose 1 of your opponent's SIGNI zones. During your opponent's next turn, your opponent can't attack with SIGNI in that SIGNI zone." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "圣魔 因波斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，正面没有精灵的场合，可以把那次攻击无效。这样做的场合，精灵区1个指定。下一个对战对手的回合期间，对战对手的那个精灵区的精灵不能攻击。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(getOppositeSIGNI() == null && playerChoiceActivate())
            {
                disableNextAttack(getCardIndex());
                
                FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();

                ChronoRecord record2 = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
                GFX.attachToChronoRecord(record2, new GFXZoneUnderIndicator(getOpponent(),fieldZone.getZoneLocation(), "chain"));
                
                addPlayerRuleCheck(PlayerRuleCheckRegistry.PlayerRuleCheckType.CAN_ATTACK, getOpponent(), record2, data ->
                    !isOwnTurn() && data.getSourceCardIndex().getLocation() == fieldZone.getZoneLocation() ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                );
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());

            draw(1);
        }
    }
}

