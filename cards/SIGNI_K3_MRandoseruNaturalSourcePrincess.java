package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_MRandoseruNaturalSourcePrincess extends Card {

    public SIGNI_K3_MRandoseruNaturalSourcePrincess()
    {
        setImageSets("SPDi43-07");
        setLinkedImageSets("SPDi43-02");

        setOriginalName("羅原姫　Mランドセル");
        setAltNames("ラゲンヒメエムランドセル Ragenhime Emu Randoseru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《まほまほ☆さんさんちくちく》がいる場合、対戦相手が手札を２枚捨てないかぎり、あなたは以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のデッキの上からカードを８枚トラッシュに置く。\n" +
                "$$2対戦相手のライフクロスが０枚の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、そのパワーを－8000する。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、対戦相手が手札を１枚捨てないかぎり、ターン終了時まで、そのパワーを－5000する。"
        );

        setName("en", "M Randoseru, Natural Source Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Mahomaho☆Three Three Sting Sting\", unless your opponent discards 2 cards from their hand, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Put the top 8 cards of your opponent's deck into the trash.\n" +
                "$$2 If your opponent has 0 life cloth, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power unless your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "罗原姬 M箱型皮质学生书包");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《まほまほ☆さんさんちくちく》的场合，如果对战对手不把手牌2张舍弃，那么你从以下的2种选1种。\n" +
                "$$1 从对战对手的牌组上面把8张牌放置到废弃区。\n" +
                "$$2 对战对手的生命护甲在0张的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@E %K:对战对手的精灵1只作为对象，如果对战对手不把手牌1张舍弃，那么直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("まほまほ☆さんさんちくちく") &&
               discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).get() == null)
            {
                if(playerChoiceMode() == 1)
                {
                    millDeck(getOpponent(), 8);
                } else {
                    if(getLifeClothCount(getOpponent()) == 0)
                    {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                        gainPower(target, -8000, ChronoDuration.turnEnd());
                    }
                }
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,1).get() == null)
            {
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
