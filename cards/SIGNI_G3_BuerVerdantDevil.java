package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G3_BuerVerdantDevil extends Card {
    
    public SIGNI_G3_BuerVerdantDevil()
    {
        setImageSets("WXDi-P00-070");
        
        setOriginalName("翠魔　ブエル");
        setAltNames("スイマブエル Suima Bueru");
        setDescription("jp",
                "~#：対戦相手のパワー５０００以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Buer, Jade Evil");
        setDescription("en",
                "~#Vanish target SIGNI on your opponent's field with power 5000 or more."
        );
        
        setName("en_fan", "Buer, Verdant Devil");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI with power 5000 or more, and banish it."
        );
        
		setName("zh_simplified", "翠魔 布耶尔");
        setDescription("zh_simplified", 
                "~#对战对手的力量5000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(13000);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(5000,0)).get();
            banish(target);
        }
    }
}
