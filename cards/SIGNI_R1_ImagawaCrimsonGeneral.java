package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_ImagawaCrimsonGeneral extends Card {
    
    public SIGNI_R1_ImagawaCrimsonGeneral()
    {
        setImageSets("WXDi-P00-050");
        
        setOriginalName("紅将　イマガワ");
        setAltNames("コウショウイマガワ Kooshoo Imagawa");
        setDescription("jp",
                "@C：あなたのターンの間、このシグニのパワーは＋７０００される。"
        );
        
        setName("en", "Imagawa, Crimson General");
        setDescription("en",
                "@C: During your turn, this SIGNI gets +7000 power."
        );
        
        setName("en_fan", "Imagawa, Crimson General");
        setDescription("en_fan",
                "@C: During your turn, this SIGNI gets +7000 power."
        );
        
		setName("zh_simplified", "红将 今川义元");
        setDescription("zh_simplified", 
                "@C :你的回合期间，这只精灵的力量+7000。\n"
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(7000));
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
