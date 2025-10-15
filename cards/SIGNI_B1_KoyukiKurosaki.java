package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_KoyukiKurosaki extends Card {

    public SIGNI_B1_KoyukiKurosaki()
    {
        setImageSets("WXDi-CP02-075");

        setOriginalName("黒崎コユキ");
        setAltNames("クロサキコユキ Kurosaki Koyuki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~{{U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。"
        );

        setName("en", "Kurosaki Koyuki");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Blue Archive>>, you may discard a card. If you do, your opponent discards a card at random.~{{U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, draw a card."
        );
        
        setName("en_fan", "Koyuki Kurosaki");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and your opponent discards it." +
                "~{{U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, draw 1 card."
        );

		setName("zh_simplified", "黑崎小雪");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。\n" +
                "~{{U你的攻击阶段开始时，可以把竖直状态的这只精灵#D。这样做的场合，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0 &&
               discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }
    }
}
