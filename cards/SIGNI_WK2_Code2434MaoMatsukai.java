package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WK2_Code2434MaoMatsukai extends Card {
    
    public SIGNI_WK2_Code2434MaoMatsukai()
    {
        setImageSets("WXDi-D02-27");
        
        setOriginalName("コード２４３４　魔使マオ");
        setAltNames("コードニジサンジマツカイマオ Koodo Nijisanji Matsukai Mao");
        
        setName("en", "Mao Matsukai, Code 2434");
        
        setName("en_fan", "Code 2434 Mao Matsukai");
        
		setName("zh_simplified", "2434代号 魔使真央");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
