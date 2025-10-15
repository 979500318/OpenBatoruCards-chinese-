package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WG1_OrigamiHolyVerdantBeauty extends Card {
    
    public SIGNI_WG1_OrigamiHolyVerdantBeauty()
    {
        setImageSets("WXDi-P07-096", "SPDi38-33");
        
        setOriginalName("聖翠美　オリガミ");
        setAltNames("セイスイビオリガミ Seisuibi Origami");
        
        setName("en", "Origami, Blessed Jade Beauty");
        
        setName("en_fan", "Origami, Holy Verdant Beauty");
        
		setName("zh_simplified", "圣翠美 折纸");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
