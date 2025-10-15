package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_G1_BangMemoriaVerdantDevil extends Card {
    
    public SIGNI_G1_BangMemoriaVerdantDevil()
    {
        setImageSets("WXDi-P08-069", "WXDi-P08-069P", "SPDi01-96");
        
        setOriginalName("翠魔　バン//メモリア");
        setAltNames("スイマバンメモリア Suima Ban Memoria");
        setDescription("jp",
                "@E @[手札を２枚まで捨てる]@：この方法で捨てたカード１枚につき【エナチャージ１】をする。"
        );
        
        setName("en", "Bang//Memoria, Jade Evil");
        setDescription("en",
                "@E @[Discard up to two cards]@: [[Ener Charge 1]] for each card discarded this way."
        );
        
        setName("en_fan", "Bang//Memoria, Verdant Devil");
        setDescription("en_fan",
                "@E @[Discard up to 2 cards from your hand]@: For each discarded card this way, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "翠魔 梆//回忆");
        setDescription("zh_simplified", 
                "@E 手牌2张最多舍弃:依据这个方法舍弃的牌的数量，每有1张就[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
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
            
            registerEnterAbility(new DiscardCost(0,2), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                enerCharge(getAbility().getCostPaidData().size());
            }
        }
    }
}
