package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_AlfouBlackClothedDiva extends Card {

    public LRIG_K0_AlfouBlackClothedDiva()
    {
        setImageSets("WXDi-P15-036", "SPDi01-71","SPDi23-05");

        setOriginalName("黒衣の歌姫　アルフォウ");
        setAltNames("コクイノウタヒメアルフォウ Kokui no Utahime Arufou");

        setName("en", "Alfou, Ebony - Clad Diva");
        
        
        setName("en_fan", "Alfou, Black-Clothed Diva");

		setName("zh_simplified", "黑衣的歌姬 阿尔芙");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
