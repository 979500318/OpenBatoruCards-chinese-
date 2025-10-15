package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_ArmoredSlimeStickyFigureOfHell extends Card {
    
    public SIGNI_B3_ArmoredSlimeStickyFigureOfHell()
    {
        setImageSets("WDK02-013");
        
        setOriginalName("魔界の粘形　アーマードスライム");
        setAltNames("マカイノネンギョウアーマードスライム Makai no Nengyou Aamaado Suraimu");
        setDescription("jp",
                "@C：あなたの手札が１枚以下であるかぎり、このシグニのパワーは＋4000される。"
        );
        
        setName("en", "Armored Slime, Sticky Figure of Hell");
        setDescription("en",
                "@C: As long as there are 1 or less cards in your hand, this SIGNI gets +4000 power."
        );
        
		setName("zh_simplified", "魔界的粘形 装甲史莱姆");
        setDescription("zh_simplified", 
                "@C :你的手牌在1张以下时，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY);
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
            return getHandCount(getOwner()) <= 1 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
