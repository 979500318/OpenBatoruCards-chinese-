package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_CodeRideAutobi extends Card {

    public SIGNI_R2_CodeRideAutobi()
    {
        setImageSets("WXK01-048");

        setOriginalName("コードライド　オートバイ");
        setAltNames("コードライドオートバイ Koodo Raido Ootobai");
        setDescription("jp",
                "\\U：このシグニがアタックしたとき、対戦相手のパワー1000以下のすべてのシグニをバニッシュする。"
        );

        setName("en", "Code Ride Autobi");
        setDescription("en",
                "\\U: Whenever this SIGNI attacks, banish all of your opponent's SIGNI with power 1000 or less."
        );

		setName("zh_simplified", "骑乘代号 摩托");
        setDescription("zh_simplified", 
                "[驾驶]@U :当这只精灵攻击时，对战对手的力量1000以下的全部的精灵破坏。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isState(CardStateFlag.IN_DRIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            banish(new TargetFilter().OP().SIGNI().withPower(0,1000).getExportedData());
        }
    }
}
