package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_KotamaOtose extends Card {

    public SIGNI_B1_KotamaOtose()
    {
        setImageSets("WXDi-CP02-074");

        setOriginalName("音瀬コタマ");
        setAltNames("オトセコタマ Otose Kotama");
        setDescription("jp",
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、カードを１枚引く。" +
                "~{{U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Otose Kotama");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If that card is <<Blue Archive>>, draw a card.~{{U: At the beginning of your attack phase, you may discard a card. If you do, your opponent discards a card at random."
        );
        
        setName("en_fan", "Kotama Otose");
        setDescription("en_fan",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If it is a <<Blue Archive>> card, draw 1 card." +
                "~{{U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and your opponent discards it."
        );

		setName("zh_simplified", "音濑小玉");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这只精灵在竖直状态的场合，你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，抽1张牌。\n" +
                "~{{U:你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@\n"
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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED))
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) || draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
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
