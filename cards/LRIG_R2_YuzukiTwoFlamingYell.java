package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_YuzukiTwoFlamingYell extends Card {
    
    public LRIG_R2_YuzukiTwoFlamingYell()
    {
        setImageSets("WXDi-P06-016");
        
        setOriginalName("炎唱　遊月・弐");
        setAltNames("エンショウユヅキニ Enshou Yuzuki Ni");
        
        setName("en", "Yuzuki Two, Blazing Chant");
        
        setName("en_fan", "Yuzuki Two, Flaming Yell");
        
		setName("zh_simplified", "炎唱 游月·贰");
        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
