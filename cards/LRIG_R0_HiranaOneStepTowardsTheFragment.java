package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_HiranaOneStepTowardsTheFragment extends Card {
    
    public LRIG_R0_HiranaOneStepTowardsTheFragment()
    {
        setImageSets("WXDi-D03-001", "SPDi01-07","SPDi02-04","SPDi04-01","SPDi05-02","SPDi06-01","SPDi11-01","SPDi13-02","SPDi15-03","SPDi21-04","SPDi24-02","SPDi42-5P", "PR-Di004");
        
        setOriginalName("欠片へ一歩　ヒラナ");
        setAltNames("カケラヘイッポヒラナ Kakera he Ippo Hirana");
        
        setName("en", "Hirana, a Spark of Hope");
        
        setName("en_fan", "Hirana, One Step Towards the Fragment");
        
		setName("zh_simplified", "向碎片一步 平和");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
