package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_BoudicaCrimsonGeneral extends Card {
    
    public SIGNI_R3_BoudicaCrimsonGeneral()
    {
        setImageSets("WXDi-P02-062");
        
        setOriginalName("紅将　ブーディカ");
        setAltNames("コウショウブーディカ Koushou Buudika");
        setDescription("jp",
                "@C：このシグニが中央のシグニゾーンにあるかぎり、このシグニのパワーは＋5000される。"
        );
        
        setName("en", "Boudica, Crimson General");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, it gets +5000 power."
        );
        
        setName("en_fan", "Boudica, Crimson General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on your center SIGNI zone, this SIGNI gets +5000 power."
        );
        
		setName("zh_simplified", "红将 布狄卡");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
        }
        
        private ConditionState onConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
