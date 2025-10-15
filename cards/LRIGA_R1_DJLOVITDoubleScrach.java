package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class LRIGA_R1_DJLOVITDoubleScrach extends Card {
    
    public LRIGA_R1_DJLOVITDoubleScrach()
    {
        setImageSets("WXDi-P01-015");
        
        setOriginalName("DJ.LOVIT-W-SCRACH");
        setAltNames("ディージェーラビットダブルスクラッチ Diijee Rabitto Daburu Sukuratchi Scratch W Scrach");
        setDescription("jp",
                "@E %X @[手札を１枚捨てる]@：あなたのシグニ１体を対象とし、ターン終了時まで、それは[[ダブルクラッシュ]]を得る。"
        );
        
        setName("en", "DJ LOVIT - SCRATCHx2");
        setDescription("en",
                "@E %X @[Discard a card]@: Target SIGNI on your field gains [[Double Crush]] until end of turn."
        );
        
        setName("en_fan", "DJ.LOVIT - DOUBLE-SCRACH");
        setDescription("en_fan",
                "@E %X @[Discard 1 card from your hand]@: Target 1 of your SIGNI, and until end of turn, it gains [[Double Crush]]."
        );
        
		setName("zh_simplified", "DJ.LOVIT-W-SCRACH");
        setDescription("zh_simplified", 
                "@E %X手牌1张舍弃:你的精灵1只作为对象，直到回合结束时为止，其得到[[双重击溃]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new DiscardCost(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
