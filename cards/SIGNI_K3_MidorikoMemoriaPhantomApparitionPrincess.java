package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.FieldZone;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class SIGNI_K3_MidorikoMemoriaPhantomApparitionPrincess extends Card {

    public SIGNI_K3_MidorikoMemoriaPhantomApparitionPrincess()
    {
        setImageSets("WXDi-P11-046", "WXDi-P11-046P");

        setOriginalName("幻怪姫　翠子//メモリア");
        setAltNames("ゲンカイキ ミドリコメモリア Genkaiki Midoriko Memoria");
        setDescription("jp",
                "@C：このシグニが中央のシグニゾーンにあるかぎり、このシグニのパワーは＋3000差れ、このシグニは@>@C：あなたのエナゾーンに緑のカードが３枚以上あるかぎり、このシグニは対戦相手の効果によってダウンしない。@@を得る。\n" +
                "@U：このシグニがアタックしたとき、このターンにあなたがピースを使用していた場合、対戦相手シグニゾーン１つを指定する。次の対戦相手のターンの間、こ対戦相手はそのシグニゾーンにあるシグニでアタックできない。"
        );

        setName("en", "Midoriko//Memoria, Phantom Queen");
        setDescription("en",
                "@C: As long as this SIGNI is in the center SIGNI Zone, it gets +3000 power and gains@>@C: As long as there are three or more green cards in your Ener Zone, this SIGNI cannot be downed by your opponent's effects.@@" +
                "@U: Whenever this SIGNI attacks, if you have used a PIECE this turn, choose one of your opponent's SIGNI Zones. During your opponent's next turn, your opponent cannot attack with the SIGNI in that SIGNI Zone."
        );
        
        setName("en_fan", "Midoriko//Memoria, Phantom Apparition Princess");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, this SIGNI gets +3000 power, and it gains:" +
                "@>@C: As long as there are 3 or more green cards in your ener zone, this SIGNI can't be downed by your opponent's effects.@@" +
                "@U: Whenever this SIGNI attacks, if you used a piece this turn, choose 1 of your opponent's SIGNI zones. During your opponent's next turn, your opponent can't attack with SIGNI in that SIGNI zone."
        );

		setName("zh_simplified", "幻怪姬 翠子//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，这只精灵的力量+3000，这只精灵得到\n" +
                "@>@C 你的能量区的绿色的牌在3张以上时，这只精灵不会因为对战对手的效果#D。@@\n" +
                "@U :当这只精灵攻击时，这个回合你把和音使用过的场合，对战对手的精灵区1个指定。下一个对战对手的回合期间，对战对手的那个精灵区的精灵不能攻击。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000), new AbilityGainModifier(this::onConstEffModGetSample));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(this::onAttachedConstEffCond, new RuleCheckModifier<>(CardRuleCheckRegistry.CardRuleCheckType.CAN_BE_DOWNED, this::onAttachedConstEffModRuleCheck));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return new TargetFilter().own().withColor(CardColor.GREEN).fromEner().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_PIECE && isOwnCard(event.getCaller())) > 0)
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();
                
                ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
                GFX.attachToChronoRecord(record, new GFXZoneUnderIndicator(getOpponent(),fieldZone.getZoneLocation(), "chain"));
                
                addPlayerRuleCheck(PlayerRuleCheckRegistry.PlayerRuleCheckType.CAN_ATTACK, getOpponent(), record, data ->
                    !isOwnTurn() && data.getSourceCardIndex().getLocation() == fieldZone.getZoneLocation() ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                );
            }
        }
    }
}
