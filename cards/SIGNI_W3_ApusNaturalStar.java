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

public final class SIGNI_W3_ApusNaturalStar extends Card {

    public SIGNI_W3_ApusNaturalStar()
    {
        setImageSets("WX25-P2-070");

        setOriginalName("羅星　アプス");
        setAltNames("ラセイアプス Rasei Apusu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋5000する。それがレツナの場合、代わりに次の対戦相手のターン終了時まで、それのパワーを＋10000する。"
        );

        setName("en", "Apus, Natural Star");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your other <<Space>> SIGNI, and until the end of your opponent's next turn, it gets +5000 power. If that SIGNI is a Resona, until the end of your opponent's next turn, it gets +10000 power instead."
        );

		setName("zh_simplified", "罗星 天燕座");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+5000。其是共鸣的场合，作为替代，直到下一个对战对手的回合结束时为止，其的力量+10000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.SPACE).except(getCardIndex())).get();

            if(target != null)
            {
                gainPower(target, target.getIndexedInstance().getTypeByRef() != CardType.RESONA ? 5000 : 10000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
