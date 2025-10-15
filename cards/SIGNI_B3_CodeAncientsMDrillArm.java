package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_CodeAncientsMDrillArm extends Card {

    public SIGNI_B3_CodeAncientsMDrillArm()
    {
        setImageSets("SPDi43-21");
        setLinkedImageSets("SPDi43-15");

        setOriginalName("コードアンシエンツ　Mドリルアーム");
        setAltNames("コードアンシエンツエムドリルアーム Koodo Anshientsu Emu Doriruaamu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、カードを１枚引いてもよい。そうした場合、対戦相手はカードを１枚引く。\n" +
                "@E：このシグニがアタックしたとき、あなたの場に《VOGUE3-EXTREME マドカ》がいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を２枚捨てる。\n" +
                "$$2対戦相手の手札が０枚の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Code Ancients M Drill Arm");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may draw 1 card. If you do, your opponent draws 1 card.\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Madoka, VOGUE 3-EXTREME\", @[@|choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 2 cards from their hand.\n" +
                "$$2 If there are 0 cards in your opponent's hand, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "古神代号 M臂钻");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以抽1张牌。这样做的场合，对战对手抽1张牌。\n" +
                "@U 当这只精灵攻击时，你的场上有《VOGUE3-EXTREME:マドカ》的场合，从以下的2种选1种。\n" +
                "$$1 对战对手把手牌2张舍弃。\n" +
                "$$2 对战对手的手牌在0张的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceActivate())
            {
                draw(1);
                draw(getOpponent(), 1);
            }
        }

        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("VOGUE3-EXTREME マドカ"))
            {
                if(playerChoiceMode() == 1)
                {
                    discard(getOpponent(), 2);
                } else if(getHandCount(getOpponent()) == 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    gainPower(target, -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
