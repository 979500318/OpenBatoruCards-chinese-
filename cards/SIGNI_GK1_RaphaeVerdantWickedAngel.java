package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_GK1_RaphaeVerdantWickedAngel extends Card {
    
    public SIGNI_GK1_RaphaeVerdantWickedAngel()
    {
        setImageSets("WXDi-P04-091", "SPDi38-40");
        
        setOriginalName("翠凶天　ラファエ");
        setAltNames("スイキョウテンラファエ Suikyouten Rafae");
        
        setName("en", "Raphae, Jade Doomed Angel");
        
        setName("en_fan", "Raphae, Verdant Wicked Angel");
        
		setName("zh_simplified", "翠凶天 拉斐尔");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
