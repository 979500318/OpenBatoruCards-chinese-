package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_FaluluPriParaIdol extends Card {

    public SIGNI_B2_FaluluPriParaIdol()
    {
        setImageSets("WXDi-P10-061");

        setOriginalName("プリパラアイドル　ファルル");
        setAltNames("プリパラアイドルファルル Puripara Aidoru Faruru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。\n" +
                "@U $T1：あなたが＜プリパラ＞のシグニを１枚捨てたとき、対戦相手のシグニ１体を対象とし、それを凍結する。"
        );

        setName("en", "Falulu, Pripara Idol");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a card. If you do, your opponent discards a card.\n" +
                "@U $T1: When you discard a <<Pripara>> SIGNI, freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Falulu, PriPara Idol");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, your opponent discards 1 card from their hand.\n" +
                "@U $T1: When you discard 1 <<PriPara>> SIGNI from your hand, target 1 of your opponent's SIGNI, and freeze it."
        );

		setName("zh_simplified", "美妙天堂偶像 法露露");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，对战对手把手牌1张舍弃。\n" +
                "@U $T1 :当你把<<プリパラ>>精灵1张舍弃时，对战对手的精灵1只作为对象，将其冻结。（冻结的精灵在下一个的自己的竖直阶段不能竖直）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            AutoAbility auto2 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                discard(getOpponent(), 1);
            }
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PRIPARA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
    }
}
