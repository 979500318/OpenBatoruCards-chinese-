package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WG2_CodeMazeBabetower extends Card {
    
    public SIGNI_WG2_CodeMazeBabetower()
    {
        setImageSets("WXDi-D04-019");
        
        setOriginalName("コードメイズ　バベタワ");
        setAltNames("コードメイズバベタワ Koodo Meizu Babetawaa");
        
        setName("en", "Babel, Code: Maze");
        
        setName("en_fan", "Code Maze Babetower");
        
		setName("zh_simplified", "迷宫代号 巴别塔");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
