package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_BrondaNaturalWarStone extends Card {

    public SIGNI_R2_BrondaNaturalWarStone()
    {
        setImageSets("WX24-D2-15");

        setOriginalName("羅闘石　ブロンダ");
        setAltNames("ラトウセキブロンダ Ratouseki Buronda");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Bronda, Natural War Stone");
        setDescription("en",
                "@U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗斗石 铜");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            AutoAbility auto = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
