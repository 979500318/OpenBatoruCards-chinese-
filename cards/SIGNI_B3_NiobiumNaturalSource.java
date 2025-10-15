package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_NiobiumNaturalSource extends Card {

    public SIGNI_B3_NiobiumNaturalSource()
    {
        setImageSets("WX25-P1-086");

        setOriginalName("羅原　Nb");
        setAltNames("ラゲンニオブ Ragen Niobu");
        setDescription("jp",
                "@U $TP $T1：あなたのシグニ１体がバニッシュされたとき、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Niobium, Natural Source");
        setDescription("en",
                "@U $TP $T1: When 1 of your SIGNI is banished, your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "罗原 Nb");
        setDescription("zh_simplified", 
                "@U $TP $T1 :当你的精灵1只被破坏时，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
    }
}
