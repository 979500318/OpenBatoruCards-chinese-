package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WG2_SurrelisHolyVerdantBeauty extends Card {
    
    public SIGNI_WG2_SurrelisHolyVerdantBeauty()
    {
        setImageSets("WXDi-P07-097");
        
        setOriginalName("聖翠美　シュレリス");
        setAltNames("セイスイビシュレリス Seisuibi Shurerisu");
        
        setName("en", "Surrelis, Blessed Jade Beauty");
        
        setName("en_fan", "Surrelis, Holy Verdant Beauty");
        
		setName("zh_simplified", "圣翠美 超现实");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
