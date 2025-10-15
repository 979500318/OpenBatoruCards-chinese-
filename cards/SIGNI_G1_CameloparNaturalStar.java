package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_CameloparNaturalStar extends Card {
    
    public SIGNI_G1_CameloparNaturalStar()
    {
        setImageSets("WXDi-D01-012", "SPDi01-29");
        
        setOriginalName("羅星　カメロパル");
        setAltNames("ラセイカメロパル Rasei Kameroparu");
        setDescription("jp",
                "@C：あなたのエナゾーンにあるシグニが持つクラスが合計３種類以上あるかぎり、このシグニのパワーは＋４０００される。"
        );
        
        setName("en", "Camelopar, Natural Planet");
        setDescription("en",
                "@C: As long as there are three or more different classes among SIGNI in your Ener Zone, this SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Camelopar, Natural Star");
        setDescription("en_fan",
                "@C: As long as there are 3 or more different classes from among SIGNI in your ener zone, this SIGNI gets +4000 power."
        );
        
		setName("zh_simplified", "罗星 鹿豹座");
        setDescription("zh_simplified", 
                "@C :你的能量区的精灵持有的类别合计3种类以上时，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
        }
        
        private ConditionState onConstEffCond()
        {
            return CardAbilities.getSIGNIClasses(getCardsInEner(getOwner())).size() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
