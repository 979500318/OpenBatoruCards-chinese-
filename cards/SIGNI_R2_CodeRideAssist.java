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

public final class SIGNI_R2_CodeRideAssist extends Card {

    public SIGNI_R2_CodeRideAssist()
    {
        setImageSets("WXK01-076");

        setOriginalName("コードライド　アシスト");
        setAltNames("コードライドアシスト Koodo Raido Ashisuto");
        setDescription("jp",
                "@U：あなたのシグニ１体がドライブ状態になったとき、対戦相手のパワー3000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Ride Assist");
        setDescription("en",
                "@U: When 1 of your SIGNI enters the drive state, target 1 of your opponent's SIGNI with power 3000 or less, and you may pay %R. If you do, banish it."
        );

		setName("zh_simplified", "骑乘代号 助力自行车");
        setDescription("zh_simplified", 
                "@U :当你的精灵1只变为驾驶状态时，对战对手的力量3000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
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

            AutoAbility auto = registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
