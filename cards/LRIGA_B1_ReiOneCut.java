package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_ReiOneCut extends Card {
    
    public LRIGA_B1_ReiOneCut()
    {
        setImageSets("WXDi-D03-009");
        
        setOriginalName("レイ＊一斬");
        setAltNames("レイイチザン Rei Ichi Zan");
        setDescription("jp",
                "@E：カードを２枚引く。"
        );
        
        setName("en", "Rei*Flash Blade");
        setDescription("en",
                "@E: Draw two cards."
        );
        
        setName("en_fan", "Rei*One Cut");
        setDescription("en_fan",
                "@E: Draw 2 cards."
        );
        
		setName("zh_simplified", "令＊一斩");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n"
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
            draw(2);
        }
    }
}
