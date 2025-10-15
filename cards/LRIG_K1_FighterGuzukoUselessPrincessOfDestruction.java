package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K1_FighterGuzukoUselessPrincessOfDestruction extends Card {

    public LRIG_K1_FighterGuzukoUselessPrincessOfDestruction()
    {
        setImageSets("WXDi-P15-022");

        setOriginalName("破壊の駄姫　闘争者グズ子");
        setAltNames("ハカイノダキトウソウシャグズコ Hakai no Daki Tousousha Guzuko");

        setName("en", "Warrior Guzuko, Queen of Destruction");
        
        
        setName("en_fan", "Fighter Guzuko, Useless Princess of Destruction");

		setName("zh_simplified", "破坏的驮姬 斗争者迟钝子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
