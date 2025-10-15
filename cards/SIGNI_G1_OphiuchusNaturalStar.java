package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_OphiuchusNaturalStar extends Card {
    
    public SIGNI_G1_OphiuchusNaturalStar()
    {
        setImageSets("WXDi-P02-073");
        
        setOriginalName("羅星　オフューカス");
        setAltNames("ラセイオヒューカス Rasei Ohyuukasu");
        setDescription("jp",
                "@E：あなたの手札からレベル１のシグニを１枚までエナゾーンに置く。\n" +
                "~#：[[エナチャージ２]]をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );
        
        setName("en", "Ophiuchus, Natural Planet");
        setDescription("en",
                "@E: Put up to one level one SIGNI from your hand into your Ener Zone." +
                "~#[[Ener Charge 2]]. Then, add up to one target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Ophiuchus, Natural Star");
        setDescription("en_fan",
                "@E: Put up to 1 level 1 SIGNI from your hand into the ener zone." +
                "~#[[Ener Charge 2]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "罗星 蛇夫座");
        setDescription("zh_simplified", 
                "@E :从你的手牌把等级1的精灵1张最多放置到能量区。" +
                "~#[[能量填充2]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().SIGNI().withLevel(1).fromHand()).get();
            putInEner(cardIndex);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(2);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
