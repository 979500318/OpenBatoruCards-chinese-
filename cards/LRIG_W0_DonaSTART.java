package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_DonaSTART extends Card {
    
    public LRIG_W0_DonaSTART()
    {
        setImageSets("SP21-001", "SP20-001","SP29-001","SP30-001","SP34-002","SP35-017", "PR-342",Mask.IGNORE+"PR-355", "WXDi-P07-016", "SPDi01-38","SPDi14-06", Mask.IGNORE+"SPDi42-2P");
        
        setOriginalName("ドーナ　ＳＴＡＲＴ");
        setAltNames("ドーナスタート Doona Sutaato");
        
        setName("en", "Dona START");
        
        setName("en_fan", "Dona START");
        
		setName("zh_simplified", "多娜 START");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setCoins(+3);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
