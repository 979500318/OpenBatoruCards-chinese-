package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_W2_MoheiheHolyGeneral extends Card {
    
    public SIGNI_W2_MoheiheHolyGeneral()
    {
        setImageSets("WXDi-P07-055");
        
        setOriginalName("聖将　モヘイヘ");
        setAltNames("セイショウモヘイヘ Seishou Moheihe");
        setDescription("jp",
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー5000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2対戦相手のパワー12000以下のシグニ１体を対象とし、#Cを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Mohayha, Blessed General");
        setDescription("en",
                "~#Choose one -- \n$$1 Return target SIGNI on your opponent's field with power 5000 or less to its owner's hand.\n$$2 You may pay #C. If you do, return target SIGNI on your opponent's field with power 12000 or less to its owner's hand."
        );
        
        setName("en_fan", "Moheihe, Holy General");
        setDescription("en_fan",
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 5000 or less, and return it to their hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay #C. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "圣将 西蒙海耶");
        setDescription("zh_simplified", 
                "~#以下选1种。#C。这样做的场合，将其返回手牌。\n" +
                "$$1 对战对手的力量5000以下的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 对战对手的力量12000以下的精灵1只作为对象，可以支付\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,5000)).get();
                addToHand(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,12000)).get();
                if(target != null && payAll(new CoinCost(1))) addToHand(target);
            }
        }
    }
}
