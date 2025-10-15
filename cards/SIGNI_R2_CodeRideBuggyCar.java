package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_R2_CodeRideBuggyCar extends Card {
    
    public SIGNI_R2_CodeRideBuggyCar()
    {
        setImageSets("WXDi-P07-066");
        
        setOriginalName("コードライド　バギーカー");
        setAltNames("コードライドバギーカー Koodo Raido Bagii Kaa");
        setDescription("jp",
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手のパワー12000以下のシグニ１体を対象とし、#Cを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Buggy Car, Code: Ride");
        setDescription("en",
                "~#Choose one -- \n$$1 Vanish target SIGNI on your opponent's field with power 5000 or less.\n$$2 You may pay #C. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Code Ride Buggy Car");
        setDescription("en_fan",
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay #C. If you do, banish it."
        );
        
		setName("zh_simplified", "骑乘代号 山地越野车");
        setDescription("zh_simplified", 
                "~#以下选1种。#C。这样做的场合，将其破坏。\n" +
                "$$1 对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手的力量12000以下的精灵1只作为对象，可以支付\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                banish(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                if(target != null && payAll(new CoinCost(1))) banish(target);
            }
        }
    }
}
