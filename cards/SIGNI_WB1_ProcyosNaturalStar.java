package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WB1_ProcyosNaturalStar extends Card {
    
    public SIGNI_WB1_ProcyosNaturalStar()
    {
        setImageSets("WXDi-D05-018", "SPDi38-32");
        
        setOriginalName("羅星　プロシオス");
        setAltNames("ラセイプロシオス Rasei Puroshiosu");
        
        setName("en", "Procyon A, Natural Planet");
        
        setName("en_fan", "Procyos, Natural Star");
        
		setName("zh_simplified", "罗星 小犬座α");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
