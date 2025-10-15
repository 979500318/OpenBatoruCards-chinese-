package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_PirulukDraw extends Card {
    
    public LRIGA_B1_PirulukDraw()
    {
        setImageSets("WXDi-P08-032");
        
        setOriginalName("ピルルク/ＤＲＡＷ");
        setAltNames("ピルルクドロー Piruruku Doroo");
        setDescription("jp",
                "@E：カードを２枚引く。"
        );
        
        setName("en", "Piruluk / Draw");
        setDescription("en",
                "@E: Draw two cards."
        );
        
        setName("en_fan", "Piruluk/DRAW");
        setDescription("en_fan",
                "@E: Draw 2 cards."
        );
        
		setName("zh_simplified", "皮璐璐可/DRAW");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PIRULUK);
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
            draw(2);
        }
    }
}
