package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_B3_LuXunAzureGeneral extends Card {
    
    public SIGNI_B3_LuXunAzureGeneral()
    {
        setImageSets("WXDi-P03-070");
        
        setOriginalName("蒼将　リクソン");
        setAltNames("ソウショウリクソン Soushou Rikuson");
        setDescription("jp",
                "@E：対戦相手の手札が５枚以上ある場合、対戦相手は手札を１枚捨てる。\n" +
                "@A %B %B %X：対戦相手の手札が０枚の場合、ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "Lu Xun, Azure General");
        setDescription("en",
                "@E: If your opponent has five or more cards in their hand, they discard a card.\n" +
                "@A %B %B %X: If your opponent has no cards in their hand, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Lu Xun, Azure General");
        setDescription("en_fan",
                "@E: If your opponent has 5 or more cards in their hand, your opponent discards 1 card from their hand.\n" +
                "@A %B %B %X: If your opponent has 0 cards in their hand, until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "苍将 陆逊");
        setDescription("zh_simplified", 
                "@E :对战对手的手牌在5张以上的场合，对战对手把手牌1张舍弃。\n" +
                "@A %B %B%X:对战对手的手牌在0张的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 2) + Cost.colorless(1)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }
        
        private void onEnterEff()
        {
            if(getHandCount(getOpponent()) >= 5)
            {
                discard(getOpponent(), 1);
            }
        }

        private ConditionState onActionEffCond()
        {
            return getHandCount(getOpponent()) != 0 ||
                   getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability -> ability.getSourceStockAbility() instanceof StockAbilityAssassin) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            if(getHandCount(getOpponent()) == 0)
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}
