package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R3_CodeRideZeroFighter extends Card {
    
    public SIGNI_R3_CodeRideZeroFighter()
    {
        setImageSets("WXK01-046");
        
        setOriginalName("コードライド　ゼロセン");
        setAltNames("コードライドゼロセン Koodo Raido Zerosen");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが10000以上の場合、対象の対戦相手のパワー7000以下のシグニ１体をバニッシュする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2[[エナチャージ１]]"
        );
        
        setName("en", "Code Ride Zero Fighter");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 10000 or more, target 1 of your opponent's SIGNI with power 7000 or less, and banish it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 3000 or less, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "骑乘代号 零战");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在10000以上的场合，对象的对战对手的力量7000以下的精灵1只破坏。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setLRIGType(CardLRIGType.LAYLA);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(getPower().getValue() >= 10000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,7000)).get();
                banish(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
