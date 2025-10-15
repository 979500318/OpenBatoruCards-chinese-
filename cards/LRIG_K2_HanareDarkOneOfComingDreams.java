package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K2_HanareDarkOneOfComingDreams extends Card {
    
    public LRIG_K2_HanareDarkOneOfComingDreams()
    {
        setImageSets("WX13-022", "SP33-036", "WXDi-P10-022");
        
        setOriginalName("来夢の冥者　ハナレ");
        setAltNames("ライムノメイジャハナレ Raimu no Meija Hanare");
        
        setName("en", "Hanare, Mourner of Dreams");
        
        setName("en_fan", "Hanare, Dark One of Coming Dreams");
        
		setName("zh_simplified", "来梦的冥者 离");
        setLRIGType(CardLRIGType.HANARE);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
