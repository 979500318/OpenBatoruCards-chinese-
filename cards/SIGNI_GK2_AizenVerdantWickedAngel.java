package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_GK2_AizenVerdantWickedAngel extends Card {
    
    public SIGNI_GK2_AizenVerdantWickedAngel()
    {
        setImageSets("WXDi-P04-092");
        
        setOriginalName("翠凶天　アイゼン");
        setAltNames("スイキョウテンアイゼン Suikyouten Aizen");
        
        setName("en", "Aizen, Jade Doomed Angel");
        
        setName("en_fan", "Aizen, Verdant Wicked Angel");
        
		setName("zh_simplified", "翠凶天 爱染明王");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
