package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_YukayukaBubbuu extends Card {
    
    public LRIGA_W2_YukayukaBubbuu()
    {
        setImageSets("WXDi-P05-023");
        
        setOriginalName("ゆかゆか☆ぶっぶー");
        setAltNames("ユカユカブッブー Yukayuka Bubbuu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E：対戦相手のシグニ１体を対象とし、対戦相手が%X %X %Xを支払わないかぎり、ターン終了時まで、それは@>@U：アタックできない。@@を得る。\n" +
                "@E %W %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "Yukayuka☆BooBoo");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn unless your opponent discards three cards.\n" +
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn unless your opponent pays %X %X %X.\n" +
                "@E %W %X %X: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn."
        );
        
        setName("en_fan", "Yukayuka☆Bubbuu");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and unless your opponent discards 3 cards from their hand, until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E: Target 1 of your opponent's SIGNI, and unless your opponent pays %X %X %X, until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %W %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@"
        );
        
		setName("zh_simplified", "由香香☆噗噗");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E 对战对手的精灵1只作为对象，如果对战对手不把%X %X %X:支付，那么直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %W%X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
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
            registerEnterAbility(this::onEnterEff2);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)), this::onEnterEff3);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
            {
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null && !payEner(getOpponent(), Cost.colorless(3)))
            {
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
    }
}
