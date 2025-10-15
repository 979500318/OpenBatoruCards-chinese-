package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WK1_MastemaHolyWickedAngel extends Card {
    
    public SIGNI_WK1_MastemaHolyWickedAngel()
    {
        setImageSets("WXDi-P05-087", "SPDi38-34");
        
        setOriginalName("聖凶天　マスティマ");
        setAltNames("セイキョウテンマスティマ Seikyouten Masutema");
        
        setName("en", "Mastema, Blessed Doomed Angel");
        
        setName("en_fan", "Mastema, Holy Wicked Angel");
        
		setName("zh_simplified", "圣凶天 莫斯提马");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
