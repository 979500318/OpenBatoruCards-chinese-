package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R2_CodeRideWagon extends Card {

    public SIGNI_R2_CodeRideWagon()
    {
        setImageSets("WDK01-015");

        setOriginalName("コードライド　ワゴン");
        setAltNames("コードライドワゴン Koodo Raido Wagon");
        setDescription("jp",
                "\\C：このシグニのパワーは＋7000される。"
        );

        setName("en", "Code Ride Wagon");
        setDescription("en",
                "\\C: This SIGNI gets +7000 power."
        );

		setName("zh_simplified", "骑乘代号 旅行车");
        setDescription("zh_simplified", 
                "[驾驶]@C :这只精灵的力量+7000。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(7000));
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.IN_DRIVE) ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
