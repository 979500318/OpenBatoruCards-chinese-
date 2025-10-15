package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_B2_MichaelaLoadingDecision extends Card {
    
    public LRIGA_B2_MichaelaLoadingDecision()
    {
        setImageSets("WXDi-P16-025");
        
        setOriginalName("先導の決断　ミカエラ");
        setAltNames("センドウノケツダンミカエラ Sendou no Ketsudan Mikaera");
        
        setName("en", "Michaela, Forefront Resolve");
        
        
        setName("en_fan", "Michaela, Loading Decision");
        
		setName("zh_simplified", "先导的决断 米卡伊来");
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
