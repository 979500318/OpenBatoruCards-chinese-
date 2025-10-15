package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.ExceedCost;

public final class SIGNI_G2_PolypterusWaterPhantom extends Card {
    
    public SIGNI_G2_PolypterusWaterPhantom()
    {
        setImageSets("WXDi-P05-075");
        
        setOriginalName("幻水　ポリプテルス");
        setAltNames("ゲンスイポリプテルス Gensui Poriputerusu");
        setDescription("jp",
                "@E @[エクシード３]@：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Polypterus, Water Phantom");
        setDescription("en",
                "@E @[Exceed 3]@: Add target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Polypterus, Water Phantom");
        setDescription("en_fan",
                "@E @[Exceed 3]@: Target 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "幻水 多鳍鱼");
        setDescription("zh_simplified", 
                "@E @[超越 3]@（从你的分身的下面把牌合计3张放置到分身废弃区）:从你的能量区把精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new ExceedCost(3), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
