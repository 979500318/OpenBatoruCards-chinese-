package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_CarnivalSTheFighter extends Card {
    
    public LRIG_G2_CarnivalSTheFighter()
    {
        setImageSets("WXDi-P15-020");
        
        setOriginalName("闘争者カーニバル　#Ｓ#");
        setAltNames("トウソウシャカーニバルワイルドセグンド Tousousha Kaanibaru Wairudo Segundo Wild Carnival S");
        
        setName("en", "Warrior Carnival #S#");
        
        
        setName("en_fan", "Carnival #S#, The Fighter");
        
		setName("zh_simplified", "斗争者嘉年华 #S#");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
