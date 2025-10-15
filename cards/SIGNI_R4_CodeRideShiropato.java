package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R4_CodeRideShiropato extends Card {

    public SIGNI_R4_CodeRideShiropato()
    {
        setImageSets("WXK01-072");

        setOriginalName("コードライド　シロパト");
        setAltNames("コードライドシロパト Koodo Raido Shiropato");
        setDescription("jp",
                "@A $T1 %R %R：対戦相手のパワー12000以下のシグニ１体を対象とし、あなたの場にドライブ状態のシグニがある場合、それをバニッシュする。"
        );

        setName("en", "Code Ride Shiropato");
        setDescription("en",
                "@A $T1 %R %R: Target 1 of your opponent's SIGNI with power 12000 or less, and if there is a SIGNI in the drive state on your field, banish it."
        );

		setName("zh_simplified", "骑乘代号 巡逻车");
        setDescription("zh_simplified", 
                "@A $T1 %R %R:对战对手的力量12000以下的精灵1只作为对象，你的场上有驾驶状态的精灵的场合，将其破坏。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(4);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && new TargetFilter().own().SIGNI().drive().getValidTargetsCount() > 0)
            {
                banish(target);
            }
        }
    }
}
