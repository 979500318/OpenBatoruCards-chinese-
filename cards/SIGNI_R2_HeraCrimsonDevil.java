package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_HeraCrimsonDevil extends Card {
    
    public SIGNI_R2_HeraCrimsonDevil()
    {
        setImageSets("WXDi-P00-052", "SPDi01-53");
        
        setOriginalName("紅魔　ヘラ");
        setAltNames("コウマヘラ Kooma Hera");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にあるすべてのシグニが赤の場合、対戦相手のパワー5000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Hera, Crimson Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if all the SIGNI on your field are red, you may pay %R. If you do, vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Hera, Crimson Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if all of your SIGNI are red, target 1 of your opponent's SIGNI with power 5000 or less, and you may pay %R. If you do, banish it."
        );
        
		setName("zh_simplified", "红魔 赫拉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上全部的精灵是红色的场合，对战对手的力量5000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.RED).getValidTargetsCount() == getSIGNICount(getOwner()))
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                
                if(cardIndex != null && payEner(Cost.color(CardColor.RED, 1)))
                {
                    banish(cardIndex);
                }
            }
        }
    }
}
