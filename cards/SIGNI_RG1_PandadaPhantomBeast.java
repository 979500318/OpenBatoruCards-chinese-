package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RG1_PandadaPhantomBeast extends Card {
    
    public SIGNI_RG1_PandadaPhantomBeast()
    {
        setImageSets("WXDi-P00-085", "SPDi38-36");
        
        setOriginalName("幻獣　パンダダ");
        setAltNames("ゲンジュウパンダダ Genjuu Pandada");
        
        setName("en", "Pandada, Phantom Terra Beast");
        
        setName("en_fan", "Pandada, Phantom Beast");
        
		setName("zh_simplified", "幻兽 熊猫猫");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
