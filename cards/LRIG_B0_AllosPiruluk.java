package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_AllosPiruluk extends Card {
    
    public LRIG_B0_AllosPiruluk()
    {
        setImageSets("PR-369", "WDK02-005", "SP20-009","SP29-005","SP30-005","SP34-004","SP35-002", "PR-397",
                     "SPK02-08B","SPK03-02","SPK04-02","SPK05-07","SPK05-08","SPK07-02","SPK08-02","SPK09-02","SPK16-2A", "WXDi-P11-014", "SPDi01-65","SPDi23-07");
        
        setOriginalName("アロス・ピルルク");
        setAltNames("アロスピルルク Arosu Piruruku");
        
        setName("en", "Allos Piruluk");
        
        setName("en_fan", "Allos Piruluk");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
