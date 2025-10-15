package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_R1_AzaelaShiningOnTheBoard extends Card {
    
    public LRIGA_R1_AzaelaShiningOnTheBoard()
    {
        setImageSets("WXDi-P16-021");
        
        setOriginalName("盤上へ煌く　アザエラ");
        setAltNames("バンジョウヘキラメクアザエラ Banjou he Kirameku Azaera");
        
        setName("en", "Azaela, Lighting Up the Board");
        
        
        setName("en_fan", "Azaela, Shining on the Board");
        
		setName("zh_simplified", "向舞台闪烁 阿左伊来");
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
