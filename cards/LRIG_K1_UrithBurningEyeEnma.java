package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_UrithBurningEyeEnma extends Card {
    
    public LRIG_K1_UrithBurningEyeEnma()
    {
        setImageSets("WD05-004", "WD14-005", "WXDi-P06-021", "WX24-D5-02", "SP03-004","SP19-005","SP33-031");
        
        setOriginalName("灼熱の閻魔　ウリス");
        setAltNames("シャクネツノエンマウリス Shakunetsu no Enma Urisu Ulith");
        
        setName("en", "Urith, Burning Enma");
        
        setName("en_fan", "Urith, Burning Eye Enma");
        
		setName("zh_simplified", "灼热的阎魔 乌莉丝");
        setLRIGType(CardLRIGType.URITH);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
