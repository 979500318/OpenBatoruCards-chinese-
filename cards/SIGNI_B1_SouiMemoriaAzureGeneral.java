package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_SouiMemoriaAzureGeneral extends Card {
    
    public SIGNI_B1_SouiMemoriaAzureGeneral()
    {
        setImageSets("WXDi-P06-061", "WXDi-P06-061P");
        
        setOriginalName("蒼将　ソウイ//メモリア");
        setAltNames("ソウショウソウイメモリア Soushou Soui Memoria");
        setDescription("jp",
                "@E @[手札を２枚まで捨てる]@：この方法で捨てたカード１枚につきカードを１枚引く。"
        );
        
        setName("en", "Soui//Memoria, Azure General");
        setDescription("en",
                "@E @[Discard up to two cards]@: Draw a card for each card discarded this way."
        );
        
        setName("en_fan", "Soui//Memoria, Azure General");
        setDescription("en_fan",
                "@E @[Discard up to 2 cards from your hand]@: Draw cards equal to the number of cards discarded this way."
        );
        
		setName("zh_simplified", "苍将 索薇//回忆");
        setDescription("zh_simplified", 
                "@E 手牌2张最多舍弃:依据这个方法舍弃的牌的数量，每有1张就抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
                draw(getAbility().getCostPaidData().size());
            }
        }
    }
}
