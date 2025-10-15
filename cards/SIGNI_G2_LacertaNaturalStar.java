package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_G2_LacertaNaturalStar extends Card {
    
    public SIGNI_G2_LacertaNaturalStar()
    {
        setImageSets("WXDi-P07-083");
        
        setOriginalName("羅星　ラセルタ");
        setAltNames("ラセイラセルタ Rasei Raseruta");
        setDescription("jp",
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー12000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手のパワー5000以上のシグニ１体を対象とし、#Cを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Lacerta, Natural Planet");
        setDescription("en",
                "~#Choose one --\n$$1 Vanish target SIGNI on your opponent's field with power 12000 or more.\n$$2 You may pay #C. If you do, vanish target SIGNI on your opponent's field with power 5000 or more."
        );
        
        setName("en_fan", "Lacerta, Natural Star");
        setDescription("en_fan",
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 12000 or more, and banish it.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 5000 or more, and you may pay #C. If you do, banish it."
        );
        
		setName("zh_simplified", "罗星 蝎虎座");
        setDescription("zh_simplified", 
                "~#以下选1种。#C。这样做的场合，将其破坏。\n" +
                "$$1 对战对手的力量12000以上的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手的力量5000以上的精灵1只作为对象，可以支付\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();
                banish(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(5000,0)).get();
                if(target != null && payAll(new CoinCost(1))) banish(target);
            }
        }
    }
}
