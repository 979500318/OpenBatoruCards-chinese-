package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_ImokoCrimsonGeneral extends Card {
    
    public SIGNI_R1_ImokoCrimsonGeneral()
    {
        setImageSets("WXDi-P06-051");
        
        setOriginalName("紅将　イモコ");
        setAltNames("コウショウイモコ Koushou Imoko");
        setDescription("jp",
                "@C：このシグニのパワーは他のシグニ１体につき＋1000される。"
        );
        
        setName("en", "Imoko, Crimson General");
        setDescription("en",
                "@C: This SIGNI gets +1000 power for every other SIGNI on the field.  "
        );
        
        setName("en_fan", "Imoko, Crimson General");
        setDescription("en_fan",
                "@C: This SIGNI gets +1000 power for every other SIGNI on the field."
        );
        
		setName("zh_simplified", "红将 小野妹子");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据其他的精灵的数量，每有1只就+1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
        }
        
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 1000 * (getSIGNICount(getOwner()) + getSIGNICount(getOpponent()) - 1);
        }
    }
}
