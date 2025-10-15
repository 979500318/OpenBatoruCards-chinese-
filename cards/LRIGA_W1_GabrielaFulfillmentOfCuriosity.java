package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_W1_GabrielaFulfillmentOfCuriosity extends Card {
    
    public LRIGA_W1_GabrielaFulfillmentOfCuriosity()
    {
        setImageSets("WXDi-P16-018");
        
        setOriginalName("創意を満たす　ガブリエラ");
        setAltNames("ソウイヲミタスガブリエラ Soui wo Mitasu Gaburiera");
        
        setName("en", "Gabriela, Brimming with Ingenuity");
        
        
        setName("en_fan", "Gabriela, Fulfillment of Curiosity");
        
		setName("zh_simplified", "盈满的创意 哲布伊来");
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
