package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_TamagoDoubleStroke extends Card {
    
    public LRIGA_B1_TamagoDoubleStroke()
    {
        setImageSets("WXDi-P02-024");
        
        setOriginalName("タマゴ＝ダブルストローク");
        setAltNames("タマゴダブルストローク Tamago Daburu Sutorooku");
        setDescription("jp",
                "@E：カードを２枚引く。"
        );
        
        setName("en", "Tamago =Double Stroke Roll=");
        setDescription("en",
                "@E: Draw two cards."
        );
        
        setName("en_fan", "Tamago-Double Stroke");
        setDescription("en_fan",
                "@E: Draw 2 cards."
        );
        
		setName("zh_simplified", "玉子=冲冲");
        setDescription("zh_simplified", 
                "@E :抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
        }
        
        private void onEnterEff()
        {
            draw(2);
        }
    }
}
