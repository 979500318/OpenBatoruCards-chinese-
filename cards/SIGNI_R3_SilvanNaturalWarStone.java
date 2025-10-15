package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R3_SilvanNaturalWarStone extends Card {

    public SIGNI_R3_SilvanNaturalWarStone()
    {
        setImageSets("WX24-D2-19");

        setOriginalName("羅闘石　シルバン");
        setAltNames("ラトウセキシルバン Ratouseki Shiruban");
        setDescription("jp",
                "@U $T1：対戦相手のシグニ１体がバニッシュされたとき、【エナチャージ１】をする。"
        );

        setName("en", "Silvan, Natural War Stone");
        setDescription("en",
                "@U $T1: When 1 of your SIGNI is banished, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗斗石 白银");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手的精灵1只被破坏时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
