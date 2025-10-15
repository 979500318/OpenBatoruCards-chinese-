package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_CodeArtDIshwasher extends Card {

    public SIGNI_B1_CodeArtDIshwasher()
    {
        setImageSets("WX24-P1-065");

        setOriginalName("コードアート　Sヨクセンキ");
        setAltNames("コードアートエスヨクセンキ Koodo Aato Esu Yokusenki");
        setDescription("jp",
                "@U: あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1手札を１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。\n" +
                "$$2手札からスペルを１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Code Art D Ishwasher");
        setDescription("en",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 You may discard 1 card from your hand. If you do, your opponent discards 1 card from their hand.\n" +
                "$$2 You may discard 1 spell from your hand. If you do, choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "必杀代号 洗碗机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 可以把手牌1张舍弃。这样做的场合，对战对手把手牌1张舍弃。\n" +
                "$$2 可以从手牌把魔法1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                if(discard(0,1).get() != null)
                {
                    discard(getOpponent(), 1);
                }
            } else if(discard(0,1, new TargetFilter().spell()).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
