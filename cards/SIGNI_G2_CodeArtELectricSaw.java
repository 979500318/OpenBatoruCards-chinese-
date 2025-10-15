package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_CodeArtELectricSaw extends Card {

    public SIGNI_G2_CodeArtELectricSaw()
    {
        setImageSets("WX25-P2-095");

        setOriginalName("コードアート　Dンノコ");
        setAltNames("コードアートディーンノコ Koodo Aato Diinnoko Electric Saw");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルがあるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、対戦相手のパワー12000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Art E Lectric Saw");
        setDescription("en",
                "@C: As long as there is a spell in your trash, this SIGNI gets +5000 power.\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, target 1 of your opponent's SIGNI with power 12000 or more, and you may pay %X. If you do, banish it." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "必杀代号 电锯");
        setDescription("zh_simplified", 
                "@C :你的废弃区有魔法时，这只精灵的力量+5000。\n" +
                "@U :你的攻击阶段开始时，这只精灵在觉醒状态的场合，对战对手的力量12000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();

                if(target != null && payEner(Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
