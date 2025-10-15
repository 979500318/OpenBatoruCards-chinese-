package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W3_CodeHeartLIONMemoria extends Card {

    public SIGNI_W3_CodeHeartLIONMemoria()
    {
        setImageSets("WXDi-P09-038", "WXDi-P09-038P");

        setOriginalName("コードハート　LION//メモリア");
        setAltNames("コードハートリオンメモリア Koodo Haato Rion Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、%W %Wを支払ってもよい。そうした場合、ターン終了時まで、このシグニは@>@U $T1：このシグニがアタックしたとき、このシグニをアップし、ターン終了時まで、このシグニは能力を失う。@@を得る。\n" +
                "@U：あなたがスペルを使用したとき、それがこのターンにあなたが使用した３枚目のスペルだった場合、このシグニは覚醒する。"
        );

        setName("en", "LION//Memoria, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, you may pay %W %W. If you do, this SIGNI gains@>@U $T1: When this SIGNI attacks, up it and it loses its abilities until end of turn.@@until end of turn.\n" +
                "@U: Whenever you use a spell, if that spell is the third spell you have used this turn, this SIGNI is awakened."
        );
        
        setName("en_fan", "Code Heart LION//Memoria");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, you may pay %W %W. If you do, until end of turn, this SIGNI gains:" + 
                "@>@U $T1: When this SIGNI attacks, up this SIGNI, and until end of turn, this SIGNI loses its abilities.@@" +
                "@U: Whenever you use a spell, if it was the third time you used a spell this turn, this SIGNI awakens."
        );

		setName("zh_simplified", "爱心代号 LION//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵在觉醒状态的场合，可以支付%W %W。这样做的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@U $T1 :当这只精灵攻击时，这只精灵竖直，直到回合结束时为止，这只精灵的能力失去。@@\n" +
                "@U :当你把魔法使用时，其是这个回合你使用的第3张的魔法的场合，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED) && payEner(Cost.color(CardColor.WHITE, 2)))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            up();
            disableAllAbilities(getCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(e -> e.getId() == GameEventId.USE_SPELL && isOwnCard(e.getCaller())) == 3)
            {
                getCardStateFlags().addValue(CardStateFlag.AWAKENED);
            }
        }
    }
}
