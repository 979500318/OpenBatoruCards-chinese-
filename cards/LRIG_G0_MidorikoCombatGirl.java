package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_MidorikoCombatGirl extends Card {
    
    public LRIG_G0_MidorikoCombatGirl()
    {
        setImageSets(Mask.IGNORE+"WD04-005", "WXDi-D08-008", "WX24-D4-01",
                Mask.IGNORE+"SP04-004","SP05-004","SP13-005A","SP13-005B","SP35-003",
                Mask.IGNORE+"PR-004",Mask.IGNORE+"PR-012","PR-016",Mask.IGNORE+"PR-126",Mask.IGNORE+"PR-161", "SPDi01-33"
        );
        
        setOriginalName("闘娘　緑姫");
        setAltNames("トウキミドリコ Touki Midoriko");
        
        setName("en", "Midoriko, Battle Girl");
        
        setName("en_fan", "Midoriko, Combat Girl");
        
		setName("zh_simplified", "斗娘 绿姬");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
