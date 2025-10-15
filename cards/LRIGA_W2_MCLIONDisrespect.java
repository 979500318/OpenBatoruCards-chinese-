package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_MCLIONDisrespect extends Card {
    
    public LRIGA_W2_MCLIONDisrespect()
    {
        setImageSets("WXDi-P02-019");
        
        setOriginalName("MC.LION-DISRESPECT");
        setAltNames("エムシーリディスリスペクト Emu Shii Rion Disurisupekuto");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E @[手札を２枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E %W %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "MC LION - DISRESPECT");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E @[Discard two cards]@: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E %W %X %X: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn."
        );
        
        setName("en_fan", "MC.LION - DISRESPECT");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E @[Discard 2 cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %W %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack."
        );
        
		setName("zh_simplified", "MC.LION-DISRESPECT");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E 手牌2张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %W%X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
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
            
            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
