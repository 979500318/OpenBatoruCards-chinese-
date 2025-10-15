package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_SasheNouvelleEternalMessenger extends Card {
    
    public LRIG_W0_SasheNouvelleEternalMessenger()
    {
        setImageSets("WD09-005", "SP12-014","SP20-007","SP35-022", Mask.IGNORE+"PR-152","PR-198",Mask.IGNORE+"PR-226", "WXDi-P11-011","WX25-P2-011", "SPDi01-64");
        
        setOriginalName("悠久の使者　サシェ・ヌーベル");
        setAltNames("ユウキュウノシシャサシェヌーベル Yuukyuu no Shisha Sashe Nuuberu");
        
        setName("en", "Sashe Nouvelle, Eternal Emissary");
        
        setName("en_fan", "Sashe Nouvelle, Eternal Messenger");
        
		setName("zh_simplified", "悠久的使者 莎榭·新月");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
