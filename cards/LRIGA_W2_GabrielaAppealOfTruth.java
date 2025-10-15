package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_W2_GabrielaAppealOfTruth extends Card {
    
    public LRIGA_W2_GabrielaAppealOfTruth()
    {
        setImageSets("WXDi-P16-019");
        
        setOriginalName("真実を募る　ガブリエラ");
        setAltNames("シンジツヲツノルガブリエラ Shinjitsu wo Tsunoru Gaburiera");
        
        setName("en", "Gabriela, Collector of Truth");
        
        
        setName("en_fan", "Gabriela, Appeal of Truth");
        
		setName("zh_simplified", "募集的真实 哲布伊来");
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
