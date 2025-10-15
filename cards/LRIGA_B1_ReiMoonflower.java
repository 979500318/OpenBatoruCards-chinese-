package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_ReiMoonflower extends Card {
    
    public LRIGA_B1_ReiMoonflower()
    {
        setImageSets("WXDi-P01-012");
        
        setOriginalName("レイ＊月華");
        setAltNames("レイゲッカ Rei Gekka");
        setDescription("jp",
                "@E：カードを１枚引く。対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Rei*Lunar Blossom");
        setDescription("en",
                "@E: Draw a card. Your opponent discards a card at random."
        );
        
        setName("en_fan", "Rei*Moonflower");
        setDescription("en_fan",
                "@E: Draw 1 card. Choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "令＊月华");
        setDescription("zh_simplified", 
                "@E :抽1张牌。不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
        }
        
        private void onEnterEff()
        {
            draw(1);
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
