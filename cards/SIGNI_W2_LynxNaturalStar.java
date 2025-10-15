package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_W2_LynxNaturalStar extends Card {
    
    public SIGNI_W2_LynxNaturalStar()
    {
        setImageSets("WXDi-P05-048");
        
        setOriginalName("羅星　リンクス");
        setAltNames("ラセイリンクス Rasei Rinkusu");
        setDescription("jp",
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Lynx, Natural Planet");
        setDescription("en",
                "~#You may pay %X. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Lynx, Natural Star");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "罗星 天猫座");
        setDescription("zh_simplified", 
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                addToHand(target);
            }
        }
    }
}
