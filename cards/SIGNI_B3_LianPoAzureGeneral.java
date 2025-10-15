package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_LianPoAzureGeneral extends Card {

    public SIGNI_B3_LianPoAzureGeneral()
    {
        setImageSets("WX25-P2-088");

        setOriginalName("蒼将　レンパ");
        setAltNames("ソウショウレンパ Soushou Renpa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。対戦相手の場に凍結状態のシグニがある場合、代わりに対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Lian Po, Azure General");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent discards 1 card from their hand. If there is a frozen SIGNI on your opponent's field, instead choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "苍将 廉颇");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手把手牌1张舍弃。对战对手的场上有冻结状态的精灵的场合，作为替代，不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);

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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() == 0)
            {
                discard(getOpponent(), 1);
            } else {
                discard(playerChoiceHand());
            }
        }
    }
}
