package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_MidorikoCombatGirl_Lostorage extends Card {
    
    public LRIG_G0_MidorikoCombatGirl_Lostorage()
    {
        setImageSets("WDK03-005","SPK03-03","SPK04-03","SPK07-03","SPK08-03","SPK09-03", "PR-K014");
        
        setOriginalName("闘娘　翠子");
        setAltNames("トウキミドリコ Touki Midoriko");

        setName("en", "Midoriko, Battle Girl");
        
        setName("en_fan", "Midoriko, Combat Girl");
        
		setName("zh_simplified", "斗娘 翠子");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
