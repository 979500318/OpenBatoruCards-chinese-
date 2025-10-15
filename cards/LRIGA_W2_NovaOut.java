package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_NovaOut extends Card {
    
    public LRIGA_W2_NovaOut()
    {
        setImageSets("WXDi-P03-024");
        
        setOriginalName("ノヴァ＝アウト");
        setAltNames("ノヴァアウト Nova Auto");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは能力を失う。\n" +
                "@E %W %X %X：能力を持たない対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E @[手札を３枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "Nova =Outro=");
        setDescription("en",
                "@E: Up to two target SIGNI on your opponent's field lose their abilities until end of turn.\n" +
                "@E %W %X %X: Return target SIGNI with no abilities on your opponent's field to its owner's hand.\n" +
                "@E @[Discard three cards]@: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn."
        );
        
        setName("en_fan", "Nova-Out");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and until end of turn, they lose their abilities.\n" +
                "@E %W %X %X: Target 1 of your opponent's SIGNI with no abilities, and return it to their hand.\n" +
                "@E @[Discard 3 cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack."
        );
        
		setName("zh_simplified", "超=放逐");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，直到回合结束时为止，这些的能力失去。\n" +
                "@E %W%X %X:不持有能力的对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@E 手牌3张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)), this::onEnterEff2);
            registerEnterAbility(new DiscardCost(3), this::onEnterEff3);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.MUTE).OP().SIGNI());
            disableAllAbilities(data, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withoutAbilities()).get();
            addToHand(target);
        }
        
        private void onEnterEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
