package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R1_BylethCrimsonDevil extends Card {
    
    public SIGNI_R1_BylethCrimsonDevil()
    {
        setImageSets("WXDi-P00-051");
        
        setOriginalName("紅魔　ビュレト");
        setAltNames("コウマビュレト Kooma Byureto");
        setDescription("jp",
                "@E %R %X：このシグニよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー１２０００以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Byleth, Crimson Evil");
        setDescription("en",
                "@E %R %X: Vanish target SIGNI on your opponent's field with power less than the power of this SIGNI." +
                "~#You may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Byleth, Crimson Devil");
        setDescription("en_fan",
                "@E %R %X: Target 1 of your opponent's SIGNI with less power than this SIGNI, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "红魔 比雷斯");
        setDescription("zh_simplified", 
                "@E %R%X:比这只精灵的力量低的对战对手的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, getCardIndex().getIndexedInstance().getPower().getValue()-1)).get();
            banish(cardIndex);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, 12000)).get();
            
            if(cardIndex != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(cardIndex);
            }
        }
    }
}
