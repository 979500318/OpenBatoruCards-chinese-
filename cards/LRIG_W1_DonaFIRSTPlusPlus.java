package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W1_DonaFIRSTPlusPlus extends Card {
    
    public LRIG_W1_DonaFIRSTPlusPlus()
    {
        setImageSets("WXDi-P07-017");
        
        setOriginalName("ドーナ　ＦＩＲＳＴ＋＋");
        setAltNames("ドーナファーストダブルプラス Doona Faasuto Daburu Purasu Double Plus");
        
        setName("en", "Dona FIRST++");
        
        setName("en_fan", "Dona FIRST++");
        
		setName("zh_simplified", "多娜 FIRST++");
        setLRIGType(CardLRIGType.DONA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
