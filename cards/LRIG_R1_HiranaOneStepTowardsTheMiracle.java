package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R1_HiranaOneStepTowardsTheMiracle extends Card {
    
    public LRIG_R1_HiranaOneStepTowardsTheMiracle()
    {
        setImageSets("WXDi-D03-002");
        
        setOriginalName("奇跡へ一歩　ヒラナ");
        setAltNames("キセキヘイッポヒラナ Kiseki he Ippo Hirana");
        
        setName("en", "Hirana, a Dream of a Miracle");
        
        setName("en_fan", "Hirana, One Step Towards the Miracle");
        
		setName("zh_simplified", "向奇迹一步 平和");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
