package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_HafniumNaturalSource extends Card {

    public SIGNI_B2_HafniumNaturalSource()
    {
        setImageSets("WX25-P1-083");

        setOriginalName("羅原　Hf");
        setAltNames("ラゲンハフニウム Ragen Hafuniumu");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、対戦相手は手札を１枚捨てる。\n" +
                "@U：対戦相手のターン終了時、このシグニをバニッシュしてもよい。"
        );

        setName("en", "Hafnium, Natural Source");
        setDescription("en",
                "@U: When this SIGNI is banished, your opponent discards 1 card from their hand.\n" +
                "@U: At the end of your opponent's turn, you may banish this SIGNI."
        );

		setName("zh_simplified", "罗原 Hf");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，对战对手把手牌1张舍弃。\n" +
                "@U :对战对手的回合结束时，可以把这只精灵破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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

            registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private void onAutoEff1()
        {
            discard(getOpponent(), 1);
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
