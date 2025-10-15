package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WK2_UrieHolyWickedAngel extends Card {
    
    public SIGNI_WK2_UrieHolyWickedAngel()
    {
        setImageSets("WXDi-P05-088");
        
        setOriginalName("聖凶天　ウリエ");
        setAltNames("セイキョウテンウリエ Seikyouten Urie");
        
        setName("en", "Uriel, Blessed Doomed Angel");
        
        setName("en_fan", "Urie, Holy Wicked Angel");
        
		setName("zh_simplified", "圣凶天 乌列尔");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
