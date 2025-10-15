package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WB2_ToutaHolyAzureGeneral extends Card {
    
    public SIGNI_WB2_ToutaHolyAzureGeneral()
    {
        setImageSets("WXDi-P05-090");
        
        setOriginalName("聖蒼将　トウタ");
        setAltNames("セイソウショトウタ Seisoushou Touta");
        
        setName("en", "Touta, Blessed Azure General");
        
        setName("en_fan", "Touta, Holy Azure General");
        
		setName("zh_simplified", "圣苍将 俵藤太");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
