package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R1_CodeRideTachisuku extends Card {

    public SIGNI_R1_CodeRideTachisuku()
    {
        setImageSets("WXK01-079");

        setOriginalName("コードライド　タチスク");
        setAltNames("コードライドタチスク Koodo Raido Tachisuku");
        setDescription("jp",
                "@U：あなたのシグニ１体がドライブ状態になったとき、対戦相手のパワー1000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Ride Tachisuku");
        setDescription("en",
                "@U: When 1 of your SIGNI enters the drive state, target 1 of your opponent's SIGNI with power 1000 or less, and you may pay %R. If you do, banish it."
        );

		setName("zh_simplified", "骑乘代号 站立式踏板车");
        setDescription("zh_simplified", 
                "@U :当你的精灵1只变为驾驶状态时，对战对手的力量1000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(1000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,1000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
