package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W2_WeiYanHolyGeneral extends Card {
    
    public SIGNI_W2_WeiYanHolyGeneral()
    {
        setImageSets("WXDi-P04-048");
        
        setOriginalName("聖将　ギエン");
        setAltNames("セイショウギエン Seishou Gien");
        setDescription("jp",
                "@C：このシグニは中央のシグニゾーンにあるかぎり、[[シャドウ（レベル３のシグニ）]]を得る。"
        );
        
        setName("en", "Wei Yan, Blessed General");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, it gains [[Shadow -- Level three SIGNI]]. "
        );
        
        setName("en_fan", "Wei Yan, Holy General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, it gains [[Shadow (level 3 SIGNI)]]."
        );
        
		setName("zh_simplified", "圣将 魏延");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，得到[[暗影（等级3的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() == 3 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
