package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_YukariKadenokoujiLongAwaitedReunion extends Card {
    
    public LRIG_G2_YukariKadenokoujiLongAwaitedReunion()
    {
        setImageSets("WX25-CP1-019");
        
        setOriginalName("勘解由小路ユカリ[待ち侘びた再会]");
        setAltNames("カデノコウジユカリマチワビタサイカイ Kadenokouji Yukari Machi Wabita Sakai");
        
        setName("en", "Kadenokouji Yukari [Long-Awaited Reunion]");
        
        setName("en_fan", "Yukari Kadenokouji [Long-Awaited Reunion]");
        
		setName("zh_simplified", "勘解由小路紫[热切期盼的重逢]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKARI);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
