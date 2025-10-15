package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_G1_GreenBerylNaturalStone extends Card {

    public SIGNI_G1_GreenBerylNaturalStone()
    {
        setImageSets("WX24-P4-102");

        setOriginalName("羅石　グリンベリル");
        setAltNames("ラセキグリンベリル Raseki Gurin Beriru");
        setDescription("jp",
                "@U $TP $T1：このシグニが対戦相手の、能力か効果の対象になったとき、【エナチャージ1】をする。"
        );

        setName("en", "Green Beryl, Natural Stone");
        setDescription("en",
                "@U $TP $T1: When this SIGNI is targeted by your opponent's ability or effect, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗石 绿柱石");
        setDescription("zh_simplified", 
                "@U $TP $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            enerCharge(1);
        }
    }
}
