package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_AkinoClap extends Card {
    
    public LRIGA_W2_AkinoClap()
    {
        setImageSets("WXDi-P03-010");
        
        setOriginalName("アキノ＊クラップ");
        setAltNames("アキノクラップ Akino Kurappu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E %X %X：対戦相手のターンの場合、あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@C：バニッシュされない。@@を得る。"
        );
        
        setName("en", "Akino*Clap");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E %X %X: If it's your opponent's turn, target SIGNI on your field gains@>@C: This SIGNI cannot be vanished.@@until end of turn."
        );
        
        setName("en_fan", "Akino*Clap");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %X %X: If it is your opponent's turn, target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't be banished."
        );
        
		setName("zh_simplified", "昭乃＊拍");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %X %X:对战对手的回合的场合，你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不会被破坏。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            if(!isOwnTurn())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, data -> RuleCheckState.BLOCK));
                    attachAbility(target, attachedConst, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
