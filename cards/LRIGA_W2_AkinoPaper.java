package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_AkinoPaper extends Card {
    
    public LRIGA_W2_AkinoPaper()
    {
        setImageSets("WXDi-D03-007");
        
        setOriginalName("アキノ＊パー");
        setAltNames("アキノパー Akino Paa");
        setDescription("jp",
                "@E：対戦相手のパワー12000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );
        
        setName("en", "Akino*Paper");
        setDescription("en",
                "@E: Return target SIGNI on your opponent's field with power 12000 or less to its owner's hand.\n" +
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn."
        );
        
        setName("en_fan", "Akino*Paper");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 12000 or less, and return it to their hand.\n" +
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack."
        );
        
		setName("zh_simplified", "昭乃＊布");
        setDescription("zh_simplified", 
                "@E :对战对手的力量12000以下的精灵1只作为对象，将其返回手牌。\n" +
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(5));
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
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,12000)).get();
            addToHand(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
