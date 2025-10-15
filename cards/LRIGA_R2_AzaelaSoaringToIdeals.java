package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_R2_AzaelaSoaringToIdeals extends Card {
    
    public LRIGA_R2_AzaelaSoaringToIdeals()
    {
        setImageSets("WXDi-P16-022");
        
        setOriginalName("理想へ羽ばたく　アザエラ");
        setAltNames("リソウヘハバタクアザエラ Risou he Habataku Azaera");
        
        setName("en", "Azaela, Soaring Towards the Ideal");
        
        
        setName("en_fan", "Azaela, Soaring to Ideals");
        
		setName("zh_simplified", "向理想振翅 阿左伊来");
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
