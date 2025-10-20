package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_G3_GrizzlyPhantomBeastDeity extends Card {

    public SIGNI_G3_GrizzlyPhantomBeastDeity()
    {
        setImageSets("WXDi-P16-052");

        setOriginalName("幻獣神　グリズリ");
        setAltNames("ゲンジュウシングリズリ Genjuushin Gurizuri");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが20000以上の場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。このシグニのパワーが30000以上の場合、代わりに対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $T1 @[アップ状態の他のシグニ１体をダウンする]@：ターン終了時まで、このシグニのパワーをこの方法でダウンしたシグニのパワーと同じだけ＋（プラス）する。"
        );

        setName("en", "Grizzly, Phantom Beast Deity");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 20000 or more, vanish target SIGNI on your opponent's field with power 8000 or less. If this SIGNI's power is 30000 or more, instead vanish target SIGNI on your opponent's field with power 12000 or less.\n@A $T1 @[Down another upped SIGNI]@: This SIGNI gets + (plus) power equal to the power of the SIGNI downed this way until end of turn."
        );
        
        setName("en_fan", "Grizzly, Phantom Beast Deity");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 20000 or more, target 1 of your opponent's SIGNI with power 8000 or less, and banish it. If this SIGNI's power is 30000 or more, target 1 of your opponent's SIGNI with power 12000 or less instead, and banish it.\n" +
                "@A $T1 @[Down 1 of your other upped SIGNI]@: Until end of turn, this SIGNI gets + (plus) the power of the SIGNI downed this way."
        );

		setName("zh_simplified", "幻兽神 灰熊");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在20000以上的场合，对战对手的力量8000以下的精灵1只作为对象，将其破坏。这只精灵的力量在30000以上的场合，作为替代，对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@A $T1 竖直状态的其他的精灵1只横置:直到回合结束时为止，这只精灵的力量+（加号）与这个方法横置的精灵的力量相同的数值。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ActionAbility act = registerActionAbility(new DownCost(new TargetFilter().SIGNI().except(cardId)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff()
        {
            if(getPower().getValue() >= 30000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                banish(target);
            } else if(getPower().getValue() >= 20000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                banish(target);
            }
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), ((CardIndexSnapshot)getAbility().getCostPaidData().get()).getPower().getValue(), ChronoDuration.turnEnd());
        }
    }
}
