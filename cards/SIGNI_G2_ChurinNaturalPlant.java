package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_ChurinNaturalPlant extends Card {
    
    public SIGNI_G2_ChurinNaturalPlant()
    {
        setImageSets("WXDi-P03-076");
        
        setOriginalName("羅植　チュリン");
        setAltNames("ラショクチュリン Rashoku Churin");
        setDescription("jp",
                "@E：このシグニが中央のシグニゾーンに出ていた場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Churin, Natural Plant");
        setDescription("en",
                "@E: If this SIGNI entered the field in your center SIGNI Zone, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Churin, Natural Plant");
        setDescription("en_fan",
                "@E: If this SIGNI is in your center SIGNI zone, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "罗植 丘林");
        setDescription("zh_simplified", 
                "@E :这只精灵在中央的精灵区出场的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(getCardIndex().getLocation() == CardLocation.SIGNI_CENTER)
            {
                enerCharge(1);
            }
        }
    }
}
