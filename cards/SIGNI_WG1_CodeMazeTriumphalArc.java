package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WG1_CodeMazeTriumphalArc extends Card {
    
    public SIGNI_WG1_CodeMazeTriumphalArc()
    {
        setImageSets("WXDi-D04-018");
        
        setOriginalName("コードメイズ　ガイセンモ");
        setAltNames("コードメイズガイセンモ Koodo Meizu Gaisenmo");
        
        setName("en", "Triomphe, Code: Maze");
        
        setName("en_fan", "Code Maze Triumphal Arc");
        
		setName("zh_simplified", "迷宫代号 凯旋门");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
