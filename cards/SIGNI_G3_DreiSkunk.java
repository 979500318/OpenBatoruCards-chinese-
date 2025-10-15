package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_DreiSkunk extends Card {

    public SIGNI_G3_DreiSkunk()
    {
        setImageSets("WXDi-P09-075");

        setOriginalName("ドライ＝スカンク");
        setAltNames("ドライスカンク Dorei Sukanku");
        setDescription("jp",
                "@U：各ターン終了時、対戦相手のパワー10000以上のシグニ１体を対象とし、%G支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@U：アタックフェイズの間、このシグニがバニッシュされたとき、対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Skunk Type: Drei");
        setDescription("en",
                "@U: At the end of each turn, you may pay %G. If you do, vanish target SIGNI on your opponent's field with power 10000 or more.\n" +
                "@U: During an attack phase, when this SIGNI is vanished, vanish target SIGNI on your opponent's field with power 10000 or more."
        );
        
        setName("en_fan", "Drei-Skunk");
        setDescription("en_fan",
                "@U: At the end of each turn, target 1 of your opponent's SIGNI with power 10000 or more, and you may pay %G. If you do, banish it.\n" +
                "@U: When this SIGNI is banished during the attack phase, target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );

		setName("zh_simplified", "DREI=臭鼬鼠");
        setDescription("zh_simplified", 
                "@U :各回合结束时，对战对手的力量10000以上的精灵1只作为对象，可以支付%G。这样做的场合，将其破坏。\n" +
                "@U :攻击阶段期间，当这只精灵被破坏时，对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(3);
        setPower(10000);

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

            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 1)))
            {
                banish(target);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
