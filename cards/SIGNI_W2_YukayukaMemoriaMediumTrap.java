package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W2_YukayukaMemoriaMediumTrap extends Card {

    public SIGNI_W2_YukayukaMemoriaMediumTrap()
    {
        setImageSets("WXDi-P09-052", "WXDi-P09-052P");

        setOriginalName("中罠　ゆかゆか//メモリア");
        setAltNames("チュウビンユカユカメモリアル Chuubin Yukayuka Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手が%Xを支払わないかぎり、ターン終了時まで、対戦相手のすベてのシグニは能力を失う。" +
                "~#：ターン終了時まで、対戦相手のすべてのシグニは能力を失う。カードを１枚引く。"
        );

        setName("en", "Yukayuka//Memoria, Medium Trickster");
        setDescription("en",
                "@U: At the beginning of your attack phase, all SIGNI on your opponent's field lose their abilities until end of turn unless your opponent pays %X." +
                "~#All SIGNI on your opponent's field lose their abilities until end of turn. Draw a card."
        );
        
        setName("en_fan", "Yukayuka//Memoria, Medium Trap");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, until end of turn, all of your opponent's SIGNI lose their abilities unless your opponent pays %X." +
                "~#Until end of turn, all of your opponent's SIGNI lose their abilities. Draw 1 card."
        );

		setName("zh_simplified", "中罠 由香香//回忆");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，如果对战对手不把%X:支付，那么直到回合结束时为止，对战对手的全部的精灵的能力失去。" +
                "~#直到回合结束时为止，对战对手的全部的精灵的能力失去。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!payEner(getOpponent(), Cost.colorless(1)))
            {
                disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
