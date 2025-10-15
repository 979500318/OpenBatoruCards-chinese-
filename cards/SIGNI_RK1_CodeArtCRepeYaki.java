package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RK1_CodeArtCRepeYaki extends Card {
    
    public SIGNI_RK1_CodeArtCRepeYaki()
    {
        setImageSets("WXDi-P07-098", "SPDi38-37");
        
        setOriginalName("コードアート　Cレプヤキ");
        setAltNames("コードアートシーレプヤキ Koodo Aato Shii Repu Yaki Crepe Yaki Crepeyaki");
        
        setName("en", "C - Repuyaki, Code: Art");
        
        setName("en_fan", "Code Art C Repe Yaki");
        
		setName("zh_simplified", "必杀代号 可丽饼机");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
