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

public final class SIGNI_K1_CodeArtMOuse extends Card {

    public SIGNI_K1_CodeArtMOuse()
    {
        setImageSets("WX25-P2-100", "SPDi45-06","SPDi45-06P");

        setOriginalName("コードアート　Mウス");
        setAltNames("コードアートエムウス Koodo Aato Emu Usu Mouse");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札から＜電機＞のシグニを１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－3000する。この効果で青の＜電機＞のシグニを捨てた場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Code Art M Ouse");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 1 <<Electric Machine>> SIGNI from your hand. If you do, until end of turn, it gets --3000 power. If you discarded a blue <<Electric Machine>> SIGNI this way, until end of turn, it gets --5000 power instead."
        );

		setName("zh_simplified", "必杀代号 鼠标");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把<<電機>>精灵1张舍弃。这样做的场合，直到回合结束时为止，其的力量-3000。这个效果把蓝色的<<電機>>精灵舍弃的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE)).get();
                
                if(cardIndex != null)
                {
                    gainPower(target, !cardIndex.getIndexedInstance().getColor().matches(CardColor.BLUE) ? -3000 : -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
