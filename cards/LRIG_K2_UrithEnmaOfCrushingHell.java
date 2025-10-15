package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K2_UrithEnmaOfCrushingHell extends Card {
    
    public LRIG_K2_UrithEnmaOfCrushingHell()
    {
        setImageSets(Mask.IGNORE+"WD05-003", "WD14-004", "WXDi-P06-022", "WX24-D5-03", Mask.IGNORE+"SP03-003","SP33-030", "WXK06-020");
        
        setOriginalName("衆合の閻魔　ウリス");
        setAltNames("シュゴウノエンマウリス Shugou no Enma Urisu Ulith");
        
        setName("en", "Urith, Fatal Enma");
        
        setName("en_fan", "Urith, Enma of Crushing Hell");
        
		setName("zh_simplified", "众合的阎魔 乌莉丝");
        setLRIGType(CardLRIGType.URITH);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
