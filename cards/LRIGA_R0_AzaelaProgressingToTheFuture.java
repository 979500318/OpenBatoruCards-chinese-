package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_R0_AzaelaProgressingToTheFuture extends Card {
    
    public LRIGA_R0_AzaelaProgressingToTheFuture()
    {
        setImageSets("WXDi-P16-020", "SPDi01-104");
        
        setOriginalName("未来へ進む　アザエラ");
        setAltNames("ミライヘススムアザエラ Mirai he Susumu Azaera");
        
        setName("en", "Azaela, Towards the Future");
        
        
        setName("en_fan", "Azaela, Progressing to the Future");
        
		setName("zh_simplified", "向未来进步 阿左伊来");
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
