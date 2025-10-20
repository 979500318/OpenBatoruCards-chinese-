package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class LRIG_R3_LalaruThird extends Card {

    public LRIG_R3_LalaruThird()
    {
        setImageSets("WX25-P2-018", "WX25-P2-018U");

        setOriginalName("ララ・ルー\"Third\"");
        setAltNames("ララルーサード Rararuu Saado");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの＜電機＞のシグニ１体を対象とし、以下の２つから１つを選ぶ。\n" +
                "$$1ターン終了時まで、それを覚醒状態にする。\n" +
                "$$2%R %Xを支払ってもよい。そうした場合、ターン終了時まで、それは【ダブルクラッシュ】を得る。\n" +
                "@A $G1 @[@|アンチェイン|@]@ #D：あなたの赤のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは【アサシン】と【シャドウ】を得る。"
        );

        setName("en", "Lalaru \"Third\"");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your <<Electric Machine>> SIGNI, and @[@|choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, it awakens.\n" +
                "$$2 You may pay %R %X. If you do, until end of turn, it gains [[Double Crush]].\n" +
                "@A $G1 @[@|Unchain|@]@ #D: Target 1 of your red SIGNI, and until the end of your opponent's next turn, it gains [[Assassin]] and [[Shadow]]."
        );

		setName("zh_simplified", "啦啦·噜“Third”");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的<<電機>>精灵1只作为对象，从以下的2种选1种。\n" +
                "$$1 直到回合结束时为止，将其变为觉醒状态。\n" +
                "$$2 可以支付%R%X。这样做的场合，直到回合结束时为止，其得到[[双重击溃]]。\n" +
                "@A $G1 解锁横置:你的红色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗杀]]和[[暗影]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LALARU);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

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
            
            ActionAbility act = registerActionAbility(new DownCost(), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Unchained");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE)).get();
            
            if(target != null)
            {
                if(playerChoiceMode() == 1)
                {
                    gainValue(target, target.getIndexedInstance().getCardStateFlags(),CardStateFlag.AWAKENED, ChronoDuration.turnEnd());
                } else if(payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
                {
                    attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
                }
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)).get();
            
            if(target != null)
            {
                attachAbility(target, new StockAbilityAssassin(), ChronoDuration.nextTurnEnd(getOpponent()));
                attachAbility(target, new StockAbilityShadow(), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
