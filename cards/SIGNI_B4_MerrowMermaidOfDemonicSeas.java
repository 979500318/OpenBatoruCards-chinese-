package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B4_MerrowMermaidOfDemonicSeas extends Card {
    
    public SIGNI_B4_MerrowMermaidOfDemonicSeas()
    {
        setImageSets("WXK01-081");
        
        setOriginalName("魔海の人魚　メロウ");
        setAltNames("マカイノニンギョメロウ Makai no Ningyo Merou");
        
        setName("en", "Merrow, Mermaid of Demonic Seas");
        
		setName("zh_simplified", "魔海的人鱼 麦罗");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(4);
        setPower(15000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
