package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_ZhaoYunHolyGeneral extends Card {
    
    public SIGNI_W1_ZhaoYunHolyGeneral()
    {
        setImageSets("WXDi-P01-046", "SPDi01-52");
        
        setOriginalName("聖将　チョウウン");
        setAltNames("セイショウチョウウン Seishou Chooun");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニは[[シャドウ（レベル２以下のシグニ）]]を得る。"
        );
        
        setName("en", "Zhao Yun, Blessed General");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow — Level two or less SIGNI]]. "
        );
        
        setName("en_fan", "Zhao Yun, Holy General");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow (level 2 or lower SIGNI)]]."
        );
        
		setName("zh_simplified", "圣将 赵云");
        setDescription("zh_simplified", 
                "@C $TP :这只精灵得到[[暗影（等级2以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
