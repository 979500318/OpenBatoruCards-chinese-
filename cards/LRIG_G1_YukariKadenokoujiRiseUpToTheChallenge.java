package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_YukariKadenokoujiRiseUpToTheChallenge extends Card {
    
    public LRIG_G1_YukariKadenokoujiRiseUpToTheChallenge()
    {
        setImageSets("WX25-CP1-018");
        
        setOriginalName("勘解由小路ユカリ[面を上げて向き合う責務]");
        setAltNames("カデノコウジユカリオモテヲアゲテムキアウセキム Kadenokouji Yukari Omote wo Agete Muki Au Sekimu");
        
        setName("en", "Kadenokouji Yukari [Rise Up to the Challenge]");
        
        setName("en_fan", "Yukari Kadenokouji [Rise Up to the Challenge]");
        
		setName("zh_simplified", "勘解由小路紫[抬头面对的责任]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKARI);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
