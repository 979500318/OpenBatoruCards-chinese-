package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_YukariKadenokouji extends Card {
    
    public LRIG_G0_YukariKadenokouji()
    {
        setImageSets("WX25-CP1-017");
        
        setOriginalName("勘解由小路ユカリ");
        setAltNames("カデノコウジユカリ Kadenokouji Yukari");
        
        setName("en", "Kadenokouji Yukari");
        
        setName("en_fan", "Yukari Kadenokouji");
        
		setName("zh_simplified", "勘解由小路紫");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKARI);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
