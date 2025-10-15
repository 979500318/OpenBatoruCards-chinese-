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
import open.batoru.data.ability.AbilityConst.PrintedValue;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_GK3_DreiChironex extends Card {

    public SIGNI_GK3_DreiChironex()
    {
        setImageSets("WX24-P4-054");

        setOriginalName("ドライ＝キロネックス");
        setAltNames("ドライキロネックス Dorai Kironekkusu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、表記されているパワーよりパワーの高い対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを表記されているパワーとの差の２倍－（マイナス）する。\n" +
                "@E：シグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。"
        );

        setName("en", "Drei-Chironex");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power higher than its printed power, and you may pay %X. If you do, until end of turn, it gets -- (minus) twice the difference between its power and its printed power.\n" +
                "@E: Target 1 SIGNI, and until end of turn, it gets +5000 power."
        );

		setName("zh_simplified", "DREI=澳大利亚箱形水母");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，比正面记载的力量高的力量的对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-（减号）与正面记载的力量的差的2倍。（正面记载的力量是，原本其印刷的数值）\n" +
                "@E :精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withPrintedPower(PrintedValue.LOWER_THAN_CURRENT)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -2 * (target.getIndexedInstance().getPower().getValue() - target.getCardReference().getPower()), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
    }
}
