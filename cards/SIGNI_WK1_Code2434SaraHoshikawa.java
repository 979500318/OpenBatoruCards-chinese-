package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WK1_Code2434SaraHoshikawa extends Card {
    
    public SIGNI_WK1_Code2434SaraHoshikawa()
    {
        setImageSets("WXDi-D02-26");
        
        setOriginalName("コード２４３４　星川サラ");
        setAltNames("コードニジサンジホシカワサラ Koodo Nijisanji Hoshikawa Sara");
        
        setName("en", "Sara Hoshikawa, Code 2434");
        
        setName("en_fan", "Code 2434 Sara Hoshikawa");
        
		setName("zh_simplified", "2434代号 星川莎拉");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
