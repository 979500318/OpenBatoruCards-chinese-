package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_AlfouInvestigatingDiva extends Card {
    
    public LRIG_K2_AlfouInvestigatingDiva()
    {
        setImageSets("WXDi-P11-022");
        
        setOriginalName("究明の歌姫　アルフォウ");
        setAltNames("キュウメイノウタヒメアルフォウ Kyuumei no Utahime Arufou");
        
        setName("en", "Alfou, Investigating Diva");
        
        setName("en_fan", "Alfou, Investigating Diva");
        
		setName("zh_simplified", "究明的歌姬 阿尔芙");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
