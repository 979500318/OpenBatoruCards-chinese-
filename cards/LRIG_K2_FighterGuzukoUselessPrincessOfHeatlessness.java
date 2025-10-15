package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_FighterGuzukoUselessPrincessOfHeatlessness extends Card {

    public LRIG_K2_FighterGuzukoUselessPrincessOfHeatlessness()
    {
        setImageSets("WXDi-P15-023");

        setOriginalName("焦熱の駄姫　闘争者グズ子");
        setAltNames("ショウネツノダキトウソウシャグズコ Shounetsu no Daki Tousousha Guzuko");

        setName("en", "Warrior Guzuko, Queen of Pyro");
        
        
        setName("en_fan", "Fighter Guzuko, Useless Princess of Heatlessness");

		setName("zh_simplified", "焦热的驮姬 斗争者迟钝子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
