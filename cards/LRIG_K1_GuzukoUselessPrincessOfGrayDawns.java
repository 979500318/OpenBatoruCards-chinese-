package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_GuzukoUselessPrincessOfGrayDawns extends Card {

    public LRIG_K1_GuzukoUselessPrincessOfGrayDawns()
    {
        setImageSets("WXDi-P09-025");

        setOriginalName("未明の駄姫　グズ子");
        setAltNames("ミメイのダキグズコ Mimei no Daki Guzuko");

        setName("en", "Guzuko, Worthless Queen of Dawn");
        
        setName("en_fan", "Guzuko, Useless Princess of Gray Dawns");

		setName("zh_simplified", "未来的驮姬 迟钝子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.KEY_CLASSIC, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
