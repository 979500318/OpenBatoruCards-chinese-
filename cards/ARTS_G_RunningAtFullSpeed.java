package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class ARTS_G_RunningAtFullSpeed extends Card {

    public ARTS_G_RunningAtFullSpeed()
    {
        setImageSets("WX24-P1-007", "WX24-P1-007U");

        setOriginalName("全力疾走");
        setAltNames("ジャイアントナックル Jaianto Nakkuru Giant Knuckle");
        setDescription("jp",
                "以下の３つから２つまで選ぶ。\n" +
                "$$1【エナチャージ３】\n" +
                "$$2あなたのエナゾーンからシグニを３枚まで選び、それらを場に出す。次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋5000する。\n" +
                "$$3次のあなたのアタックフェイズ開始時、あなたのシグニ１体を対象とし、ターン終了時まで、それは【Ｓランサー】を得る。"
        );

        setName("en", "Running at Full Speed");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 [[Ener Charge 3]]\n" +
                "$$2 Choose up to 3 SIGNI from your ener zone, and put them onto the field. Until the end of your opponent's next turn, all of your SIGNI get +5000 power.\n" +
                "$$3 At the beginning of your attack phase, target 1 of your SIGNI, and until end of turn, it gains [[S Lancer]]."
        );

		setName("zh_simplified", "全力疾走");
        setDescription("zh_simplified", 
                "从以下的3种选2种最多。\n" +
                "$$1 [[能量填充3]]。\n" +
                "$$2 从你的能量区选精灵3张最多，将这些出场。直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+5000。\n" +
                "$$3 下一个你的攻击阶段开始时，你的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(0,2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                enerCharge(3);
            }
            if((modes & 1<<1) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable());
                putOnField(data);
                
                gainPower(getSIGNIOnField(getOwner()), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
            if((modes & 1<<2) != 0)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }
    }
}

