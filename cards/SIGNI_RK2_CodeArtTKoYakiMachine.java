package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RK2_CodeArtTKoYakiMachine extends Card {
    
    public SIGNI_RK2_CodeArtTKoYakiMachine()
    {
        setImageSets("WXDi-P07-099");
        
        setOriginalName("コードアート　Tコヤキキ");
        setAltNames("コードアートティーコヤキキ Koodo Aato Tii Ko Yakiki Tako yaki Takoyaki");
        
        setName("en", "T - Koyakiki, Code: Art");
        
        setName("en_fan", "Code Art T Ko Yaki Machine");
        
		setName("zh_simplified", "必杀代号 章鱼烧机");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
