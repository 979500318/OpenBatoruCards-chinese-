package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_HanareDarkOneOfResidualRegrets extends Card {
    
    public LRIG_K1_HanareDarkOneOfResidualRegrets()
    {
        setImageSets("WX13-024", "SP33-037", "WXDi-P10-021");
        
        setOriginalName("残悔の冥者　ハナレ");
        setAltNames("ザンカイノメイジャハナレ Zankai no Meija Hanare");
        
        setName("en", "Hanare, Mourner of Regrets");
        
        setName("en_fan", "Hanare, Dark One of Residual Regrets");
        
		setName("zh_simplified", "残悔的冥者 离");
        setLRIGType(CardLRIGType.HANARE);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
