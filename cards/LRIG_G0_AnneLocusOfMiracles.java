package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_AnneLocusOfMiracles extends Card {
    
    public LRIG_G0_AnneLocusOfMiracles()
    {
        setImageSets("SP07-005", Mask.IGNORE+"SP08-004","SP16-003","SP18-005","SP35-015", "PR-063","PR-081",Mask.IGNORE+"PR-131","PR-243","PR-437", "WXDi-P08-016", "WX24-P3-023", "SPDi01-43");
        
        setOriginalName("奇跡の軌跡　アン");
        setAltNames("キセキノキセキアン Kiseki no Kiseki An");
        
        setName("en", "Ann, Marvel of Miracles");
        
        setName("en_fan", "Anne, Locus of Miracles");
        
		setName("zh_simplified", "奇迹的轨迹 安");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
