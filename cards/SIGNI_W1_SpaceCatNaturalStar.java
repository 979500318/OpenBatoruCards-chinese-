package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W1_SpaceCatNaturalStar extends Card {

    public SIGNI_W1_SpaceCatNaturalStar()
    {
        setImageSets("WX25-P2-064");

        setOriginalName("羅星　スペースキャット");
        setAltNames("ラセイスペースキャット Rasei Supeesu Kyatto");
        setDescription("jp",
                "@U：あなたのターン終了時、次の対戦相手のターン終了時まで、あなたのすべての＜宇宙＞のシグニのパワーを＋2000する。"
        );

        setName("en", "Space Cat, Natural Star");
        setDescription("en",
                "@U: At the end of your turn, until the end of your opponent's next turn, all of your <<Space>> SIGNI get +2000 power."
        );

		setName("zh_simplified", "罗星 宇宙猫");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，直到下一个对战对手的回合结束时为止，你的全部的<<宇宙>>精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.SPACE).getExportedData(), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
