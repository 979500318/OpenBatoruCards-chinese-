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

public final class LRIGA_K2_NanashiLocking extends Card {
    
    public LRIGA_K2_NanashiLocking()
    {
        setImageSets("WXDi-P07-037");
        
        setOriginalName("ナナシ・施錠");
        setAltNames("ナナシセジョウ Nanashi Sejou");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。\n" +
                "@E @[手札を２枚捨てる]@：対戦相手の感染状態のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E %K %X %X：対戦相手の感染状態のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "Nanashi Locking");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "@E @[Discard two cards]@: Target infected SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E %K %X %X: Target infected SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn."
        );
        
        setName("en_fan", "Nanashi Locking");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "@E @[Discard 2 cards from your hand]@: Target 1 of your opponent's infected SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %K %X %X: Target 1 of your opponent's infected SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@"
        );
        
		setName("zh_simplified", "无名·施锭");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "@E 手牌2张舍弃:对战对手的感染状态的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %K%X %X:对战对手的感染状态的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff2);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI().infected()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
