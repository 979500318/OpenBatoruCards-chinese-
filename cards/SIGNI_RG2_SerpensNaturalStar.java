package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RG2_SerpensNaturalStar extends Card {
    
    public SIGNI_RG2_SerpensNaturalStar()
    {
        setImageSets("WXDi-P06-089");
        
        setOriginalName("羅星　セルペンス");
        setAltNames("ラセイセルペンス Rasei Serupensu");
        
        setName("en", "Serpens, Natural Planet");
        
        setName("en_fan", "Serpens, Natural Star");
        
		setName("zh_simplified", "罗星 巨蛇座");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
