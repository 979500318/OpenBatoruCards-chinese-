package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WB1_YorishigeHolyAzureGeneral extends Card {
    
    public SIGNI_WB1_YorishigeHolyAzureGeneral()
    {
        setImageSets("WXDi-P05-089");
        
        setOriginalName("聖蒼将　ヨリシゲ");
        setAltNames("セイソウショヨリシゲ Seisoushou Yorishige");
        
        setName("en", "Yorishige, Blessed Azure General");
        
        setName("en_fan", "Yorishige, Holy Azure General");
        
		setName("zh_simplified", "圣苍将 诹访赖重");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
