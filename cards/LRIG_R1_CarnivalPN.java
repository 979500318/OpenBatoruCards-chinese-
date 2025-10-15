package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_CarnivalPN extends Card {

    public LRIG_R1_CarnivalPN()
    {
        setImageSets("WXDi-P09-012");

        setOriginalName("カーニバル　―PN―");
        setAltNames("カーニバルプリメイロネオ Kaanibaru Purimeiro Neo");

        setName("en", "Carnival -PN-");
        
        setName("en_fan", "Carnival -PN-");

		setName("zh_simplified", "嘉年华 -PN-");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
