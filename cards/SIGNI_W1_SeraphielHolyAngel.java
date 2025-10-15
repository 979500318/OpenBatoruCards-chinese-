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

public final class SIGNI_W1_SeraphielHolyAngel extends Card {

    public SIGNI_W1_SeraphielHolyAngel()
    {
        setImageSets("WX24-P4-056");

        setOriginalName("聖天　セラフィエル");
        setAltNames("セイテンセラフィエル Seiten Serafieru");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他の白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋4000する。"
        );

        setName("en", "Seraphiel, Holy Angel");
        setDescription("en",
                "@U: At the end of your turn, target 1 of your other white SIGNI, and until the end of your opponent's next turn, it gets +4000 power."
        );

		setName("zh_simplified", "圣天 撒纳斐尔");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的其他的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.WHITE).except(getCardIndex())).get();
            if(target != null) gainPower(target, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
