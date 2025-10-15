package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_IkebanaNaturalPlant extends Card {

    public SIGNI_G2_IkebanaNaturalPlant()
    {
        setImageSets("WX24-P2-084");

        setOriginalName("羅植　イケバナ");
        setAltNames("ラショクイケバナ Rashoku Ikebana");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜植物＞のシグニがある場合、【エナチャージ１】をする。"
        );

        setName("en", "Ikebana, Natural Plant");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Plant>> SIGNI on your field, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗植 花道");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<植物>>精灵的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(5000);

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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PLANT).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
    }
}
