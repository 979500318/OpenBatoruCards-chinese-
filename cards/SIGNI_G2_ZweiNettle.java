package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_G2_ZweiNettle extends Card {
    
    public SIGNI_G2_ZweiNettle()
    {
        setImageSets("WXDi-P05-073");
        
        setOriginalName("ツヴァイ＝イラクサ");
        setAltNames("ツヴァイイラクサ Zuwai Irakusa");
        setDescription("jp",
                "~#：対戦相手のパワー5000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをエナゾーンに置く。"
        );
        
        setName("en", "Nettle Type: Zwei");
        setDescription("en",
                "~#You may pay %X. If you do, put target SIGNI on your opponent's field with power 5000 or more into its owner's Ener Zone."
        );
        
        setName("en_fan", "Zwei-Nettle");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI with power 5000 or more, and you may pay %X. If you do, put it into the ener zone."
        );
        
		setName("zh_simplified", "ZWEI=荨麻");
        setDescription("zh_simplified", 
                "~#对战对手的力量5000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其放置到能量区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(5000,0)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                putInEner(target);
            }
        }
    }
}
