package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K2_DeadLeavesNaturalPlant extends Card {

    public SIGNI_K2_DeadLeavesNaturalPlant()
    {
        setImageSets("WX24-P2-093");

        setOriginalName("羅植　カレハ");
        setAltNames("ラショクカレハ Rashoku Kareha");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのトラッシュから＜植物＞のシグニ１枚を対象とし、アップ状態のこのシグニをダウンしてもよい。そうした場合、それをエナゾーンに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Dead Leaves, Natural Plant");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 <<Plant>> SIGNI from your trash, and you may down this upped SIGNI. If you do, put it into the ener zone." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "罗植 枯叶");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，从你的废弃区把<<植物>>精灵1张作为对象，可以把竖直状态的这只精灵#D。这样做的场合，将其放置到能量区。" +
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(8000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.PLANT).fromTrash()).get();
            if(target != null && !isState(CardStateFlag.DOWNED) &&
               playerChoiceActivate() && down())
            {
                putInEner(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
