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
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W1_NovaMute extends Card {
    
    public LRIGA_W1_NovaMute()
    {
        setImageSets("WXDi-P01-021");
        
        setOriginalName("ノヴァ＝ミュート");
        setAltNames("ノヴァミュート Nova Myuuto");
        setDescription("jp",
                "@E %X @[手札を１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "Nova =Mute=");
        setDescription("en",
                "@E %X @[Discard a card]@: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack@@until end of turn."
        );
        
        setName("en_fan", "Nova-Mute");
        setDescription("en_fan",
                "@E %X @[Discard 1 card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack."
        );
        
		setName("zh_simplified", "超=弱音");
        setDescription("zh_simplified", 
                "@E %X手牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
