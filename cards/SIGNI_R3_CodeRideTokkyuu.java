package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_CodeRideTokkyuu extends Card {

    public SIGNI_R3_CodeRideTokkyuu()
    {
        setImageSets("WDK01-013");

        setOriginalName("コードライド　トッキュウ");
        setAltNames("コードライドトッキュウ Koodo Raido Tokkyuu");
        setDescription("jp",
                "@C：あなたの場にドライブ状態のシグニがあるかぎり、このシグニのパワーは＋4000される。"
        );

        setName("en", "Code Ride Tokkyuu");
        setDescription("en",
                "@C: As long as there is a SIGNI in the drive state on your field, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "骑乘代号 特别急行列车");
        setDescription("zh_simplified", 
                "@C :你的场上有驾驶状态的精灵时，这只精灵的力量+4000。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().drive().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
