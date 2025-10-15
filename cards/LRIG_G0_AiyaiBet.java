package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_AiyaiBet extends Card {
    
    public LRIG_G0_AiyaiBet()
    {
        setImageSets("WD12-005", "SP12-016","SP20-010","SP35-023", Mask.IGNORE+"PR-229","PR-281", "WXDi-P11-017","WX25-P2-023", "SPDi01-70");
        
        setOriginalName("アイヤイ★ベット");
        setAltNames("アイヤイベット Aiyai Betto");
        
        setName("en", "Aiyai ★ Bet");
        
        setName("en_fan", "Aiyai★Bet");
        
		setName("zh_simplified", "艾娅伊★下注");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
