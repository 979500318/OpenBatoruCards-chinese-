package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_W0_GabrielaWeavingOfWill extends Card {
    
    public LRIGA_W0_GabrielaWeavingOfWill()
    {
        setImageSets("WXDi-P16-017", "SPDi01-103");
        
        setOriginalName("意思を紡ぐ　ガブリエラ");
        setAltNames("イシヲツムグガブリエラ Ishi wo Tsumugu Gaburiera");
        
        setName("en", "Gabriela, Weaver of Will");
        
        
        setName("en_fan", "Gabriela, Weaving of Will");
        
		setName("zh_simplified", "纺织的决意 哲布伊来");
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
