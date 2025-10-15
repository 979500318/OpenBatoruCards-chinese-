package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R1_CodeRideCargo extends Card {
    
    public SIGNI_R1_CodeRideCargo()
    {
        setImageSets("WXDi-D07-013");
        
        setOriginalName("コードライド　カモツ");
        setAltNames("コードライドカモツ Koodo Raido Kamotsu");
        setDescription("jp",
                "@E：対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置いてもよい。そうした場合、対戦相手は[[エナチャージ１]]をしてもよい。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Cargo, Code: Ride");
        setDescription("en",
                "@E: You may put target card from your opponent's Ener Zone into their trash. If you do, your opponent may [[Ener Charge 1]]." +
                "~#You may pay %R. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Code Ride Cargo");
        setDescription("en_fan",
                "@E: Target 1 card from your opponent's ener zone, and you may put it into the trash. If you do, your opponent may [[Ener Charge 1]]." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %R. If you do, banish it."
        );
        
		setName("zh_simplified", "骑乘代号 货运专列");
        setDescription("zh_simplified", 
                "@E :从对战对手的能量区把1张牌作为对象，可以将其放置到废弃区。这样做的场合，对战对手可以[[能量填充1]]。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            
            if(target != null && playerChoiceActivate() &&
               trash(target) && playerChoiceActivate(getOpponent()))
            {
                enerCharge(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
