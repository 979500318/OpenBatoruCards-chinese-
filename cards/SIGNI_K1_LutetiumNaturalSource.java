package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_LutetiumNaturalSource extends Card {

    public SIGNI_K1_LutetiumNaturalSource()
    {
        setImageSets("WX25-P1-102");

        setOriginalName("羅原　Lu");
        setAltNames("ラゲンルテチウム Ragen Rutechiumu");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。\n" +
                "@U：対戦相手のターン終了時、このシグニをバニッシュしてもよい。"
        );

        setName("en", "Lutetium, Natural Source");
        setDescription("en",
                "@U: When this SIGNI is banished, target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --5000 power.\n" +
                "@U: At the end of your opponent's turn, you may banish this SIGNI."
        );

		setName("zh_simplified", "罗原 Lu");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-5000。\n" +
                "@U :对战对手的回合结束时，可以把这只精灵破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate())
            {
                banish(getCardIndex());
            }
        }
    }
}
