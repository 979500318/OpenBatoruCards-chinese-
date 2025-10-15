package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_CarnivalPTheFighter extends Card {
    
    public LRIG_G1_CarnivalPTheFighter()
    {
        setImageSets("WXDi-P15-019");
        
        setOriginalName("闘争者カーニバル　#Ｐ#");
        setAltNames("トウソウシャカーニバルワイルドプリメイロ Tousousha Kaanibaru Wairudo Purimeiro Wild Carnival P");
        
        setName("en", "Warrior Carnival #P#");
        
        
        setName("en_fan", "Carnival #P#, The Fighter");
        
		setName("zh_simplified", "斗争者嘉年华 #P#");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
