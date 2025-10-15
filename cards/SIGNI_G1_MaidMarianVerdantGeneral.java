package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_G1_MaidMarianVerdantGeneral extends Card {
    
    public SIGNI_G1_MaidMarianVerdantGeneral()
    {
        setImageSets("WXDi-P01-070");
        
        setOriginalName("翠将　メイドマリアン");
        setAltNames("スイショウメイドマリアン Suishou Meido Marian");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、あなたのエナゾーンから＜武勇＞のシグニ１枚を対象とし、%Xを支払ってもよい。そうした場合、それを手札に加える。" +
                "~#：対戦相手のシグニを１体を対象とし、%G %Gを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Maid Marian, Jade General");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may pay %X. If you do, add target <<Brave>> SIGNI from your Ener Zone to your hand." +
                "~#You may pay %G %G. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Maid Marian, Verdant General");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, target 1 <<Valor>> SIGNI from your ener zone, and you may pay %X. If you do, add it to your hand." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %G %G. If you do, banish it."
        );
        
		setName("zh_simplified", "翠将 女仆玛丽安");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，从你的能量区把<<武勇>>精灵1张作为对象，可以支付%X。这样做的场合，将其加入手牌。" +
                "~#对战对手的精灵1只作为对象，可以支付%G %G。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromEner()).get();
            if(target != null && payEner(Cost.colorless(1)) && target.getLocation() == CardLocation.ENER)
            {
                addToHand(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            if(target != null && payEner(Cost.color(CardColor.GREEN, 2)))
            {
                banish(target);
            }
        }
    }
}
