package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_CodeRideLaylaDissona extends Card {

    public SIGNI_R2_CodeRideLaylaDissona()
    {
        setImageSets("WXDi-P16-068");

        setOriginalName("コードライド　レイラ//ディソナ");
        setAltNames("コードライドレイラディソナ Koodo Raidu Reira Disona");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Layla//Dissona, Code: Ride");
        setDescription("en",
                "@U $T1: When this SIGNI crushes one of your opponent's Life Cloth, you may pay %R. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Code Ride Layla//Dissona");
        setDescription("en_fan",
                "@U $T1: When this SIGNI crushes 1 of your opponent's life cloth, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %R. If you do, banish it."
        );

		setName("zh_simplified", "骑乘代号 蕾拉//失调");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵把对战对手的生命护甲1张击溃时，对战对手的力量8000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
