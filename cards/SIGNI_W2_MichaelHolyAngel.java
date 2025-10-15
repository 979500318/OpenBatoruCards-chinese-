package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_MichaelHolyAngel extends Card {
    
    public SIGNI_W2_MichaelHolyAngel()
    {
        setImageSets("WXDi-D04-013");
        
        setOriginalName("聖天　ミカエル");
        setAltNames("セイテンミカエル Seiten Mikaeru");
        setDescription("jp",
                "@C：このシグニが左か右のシグニゾーンにあるかぎり、あなたの中央のシグニゾーンにあるシグニのパワーを＋3000する。"
        );
        
        setName("en", "Michael, Blessed Angel");
        setDescription("en",
                "@C: As long as this SIGNI is in the left or right SIGNI Zone, SIGNI in your center SIGNI Zone get +3000 power."
        );
        
        setName("en_fan", "Michael, Holy Angel");
        setDescription("en_fan",
                "@C: As long as this card is on your left or right SIGNI zone, the SIGNI on your center SIGNI zone gets +3000 power."
        );
        
		setName("zh_simplified", "圣天 米迦勒");
        setDescription("zh_simplified", 
                "@C :这只精灵在左侧或右侧的精灵区时，你的中央的精灵区的精灵的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().own().SIGNI(), new PowerModifier(3000));
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return (getCardIndex().getLocation() == CardLocation.SIGNI_LEFT || getCardIndex().getLocation() == CardLocation.SIGNI_RIGHT) &&
                    cardIndex.getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
