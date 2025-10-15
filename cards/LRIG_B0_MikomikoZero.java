package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_MikomikoZero extends Card {
    
    public LRIG_B0_MikomikoZero()
    {
        setImageSets("WXDi-P05-011", "SPDi01-25","SPDi13-01", Mask.IGNORE+"SPDi42-3P");
        
        setOriginalName("みこみこ☆ぜろ");
        setAltNames("みこみこぜろ Mikomiko Zero");
        
        setName("en", "Mikomiko☆Zero");
        
        setName("en_fan", "Mikomiko☆Zero");
        
		setName("zh_simplified", "美琴琴☆零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
