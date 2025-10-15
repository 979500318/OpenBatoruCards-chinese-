package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_GK1_CodeMazeShibamaTaisha extends Card {

    public SIGNI_GK1_CodeMazeShibamaTaisha()
    {
        setImageSets("WX24-P4-099");

        setOriginalName("コードメイズ　シバマタイシャ");
        setAltNames("コードメイズシバマタイシャ Koodo Meizu Shibana Taisha");

        setName("en", "Code Maze Shibama-Taisha");

		setName("zh_simplified", "迷宫代号 柴又帝释天");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
