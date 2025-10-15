package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_DecarabiaCrimsonDevil extends Card {
    
    public SIGNI_R2_DecarabiaCrimsonDevil()
    {
        setImageSets("WXDi-P05-057");
        
        setOriginalName("紅魔　デカラビア");
        setAltNames("コウマデカラビア Kouma Dekarabia");
        setDescription("jp",
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Decarabia, Crimson Evil");
        setDescription("en",
                "~#You may pay %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Decarabia, Crimson Devil");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, banish it."
        );
        
		setName("zh_simplified", "红魔 德卡拉比亚");
        setDescription("zh_simplified", 
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
