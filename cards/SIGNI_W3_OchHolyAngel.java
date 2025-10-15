package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W3_OchHolyAngel extends Card {

    public SIGNI_W3_OchHolyAngel()
    {
        setImageSets("WX25-P1-070");

        setOriginalName("聖天　オク");
        setAltNames("セイテンオク Seiten Oku");
        setDescription("jp",
                "@U：あなたのターン終了時、次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。"
        );

        setName("en", "Och, Holy Angel");
        setDescription("en",
                "@U: At the end of your turn, until the end of your opponent's next turn, all of your SIGNI get +3000 power."
        );

		setName("zh_simplified", "圣天 奥曲");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
