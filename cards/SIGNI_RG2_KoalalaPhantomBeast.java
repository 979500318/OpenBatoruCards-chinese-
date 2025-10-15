package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RG2_KoalalaPhantomBeast extends Card {
    
    public SIGNI_RG2_KoalalaPhantomBeast()
    {
        setImageSets("WXDi-D01-019");
        
        setOriginalName("幻獣　コアララ");
        setAltNames("ゲンジュウコアララ Genjuu Koarara");
        
        setName("en", "Koalala, Phantom Terra Beast");
        
        setName("en_fan", "Koalala, Phantom Beast");
        
		setName("zh_simplified", "幻兽 考拉拉");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
