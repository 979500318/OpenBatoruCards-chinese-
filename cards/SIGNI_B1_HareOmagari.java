package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_HareOmagari extends Card {

    public SIGNI_B1_HareOmagari()
    {
        setImageSets("WX25-CP1-063");

        setOriginalName("小鈎ハレ");
        setAltNames("オマガリハレ Omagari Hare");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜ブルアカ＞のシグニがある場合、対戦相手は手札を１枚捨てる。" +
                "~{{U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Omagari Hare");

        setName("en_fan", "Hare Omagari");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Blue Archive>> SIGNI on your field, your opponent discards 1 card from their hand." +
                "~{{U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "小钩晴");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有其他的<<ブルアカ>>精灵的场合，对战对手把手牌1张舍弃。\n" +
                "~{{U:你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onAutoEff1()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                discard(getOpponent(), 1);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
