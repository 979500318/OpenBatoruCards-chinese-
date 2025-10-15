package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_KryptonNaturalSource extends Card {

    public SIGNI_B1_KryptonNaturalSource()
    {
        setImageSets("WXDi-P15-090");

        setOriginalName("羅原　Kｒ");
        setAltNames("ラゲンクリプトン Ragen Kuriputon Kr");
        setDescription("jp",
                "@U $T1：対戦相手のターンの間、あなたの＜原子＞のシグニ１体がバニッシュされたとき、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Kr, Natural Element");
        setDescription("en",
                "@U $T1: During your opponent's turn, when an <<Atom>> SIGNI on your field is vanished, your opponent discards a card."
        );
        
        setName("en_fan", "Krypton, Natural Source");
        setDescription("en_fan",
                "@U $T1: During your opponent's turn, when 1 of your <<Atom>> SIGNI is banished, your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "罗原 Kr");
        setDescription("zh_simplified", 
                "@U $T1 :对战对手的回合期间，当你的<<原子>>精灵1只被破坏时，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return !isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ATOM) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
    }
}
