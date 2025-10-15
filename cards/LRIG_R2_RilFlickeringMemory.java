package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_RilFlickeringMemory extends Card {
    
    public LRIG_R2_RilFlickeringMemory()
    {
        setImageSets("WX15-008", "WXK02-013", "WXDi-P07-015");
        
        setOriginalName("明滅の記憶　リル");
        setAltNames("メイメツノキオクリル Meimetsu no Kioku Riru");
        
        setName("en", "Ril, Memory of Flickering ");
        
        setName("en_fan", "Ril, Flickering Memory");
        
		setName("zh_simplified", "明灭的记忆 莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
