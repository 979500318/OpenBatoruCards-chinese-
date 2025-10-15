package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_AlfouDanseuseOfPerformancesBeginning extends Card {
    
    public LRIG_K0_AlfouDanseuseOfPerformancesBeginning()
    {
        setImageSets("WDK17-005", "SPK03-22","SPK04-22");
        
        setOriginalName("カイエンノマイヒメアルフォウ 開演の舞姫　アルフォウ");
        setAltNames("Kaien no Maihime Arufou");
        
        setName("en", "Alfou, Danseuse of Performance's Beginning");
        
		setName("zh_simplified", "开演的舞姬 阿尔芙");
        setLRIGType(CardLRIGType.ALFOU);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
