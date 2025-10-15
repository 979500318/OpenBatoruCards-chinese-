package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_AlfouBlackClothedBride extends Card {
    
    public LRIG_K0_AlfouBlackClothedBride()
    {
        setImageSets(Mask.IGNORE+"PR-000A", "SP35-026", "PR-280", "WX24-P3-027");
        
        setOriginalName("黒衣の歌姫　アルフォウ");
        setAltNames("コクイノウタヒメアルフォウ Kokui no Hanayome Arufou");
        
        setName("en_fan", "Alfou, Black-Clothed Bride");
        
		setName("zh_simplified", "黑衣的花嫁 阿尔芙");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
