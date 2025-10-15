package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckCanPayEner;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_HiranaStamp extends Card {
    
    public LRIGA_R2_HiranaStamp()
    {
        setImageSets("WXDi-P03-012");
        
        setOriginalName("ヒラナ＊スタンプ");
        setAltNames("ヒラナスタンプ Hirana Sutanpu");
        setDescription("jp",
                "@E：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X %X %X %X %X：対戦相手のターンの場合、このターン、対戦相手は１以上のエナコストを支払えない。"
        );
        
        setName("en", "Hirana*Stamp");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "@E %X %X %X %X %X: If it's your opponent's turn, your opponent cannot pay Ener costs with a value of one or more this turn.\n" +
                " "
        );
        
        setName("en_fan", "Hirana*Stamp");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@E %X %X %X %X %X: If it is your opponent's turn, this turn, your opponent can't pay ener costs of 1 or more."
        );
        
		setName("zh_simplified", "平和＊封印");
        setDescription("zh_simplified", 
                "@E :对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %X %X %X %X %X:对战对手的回合的场合，这个回合，对战对手不能把1以上的能量费用支付。\n" +
                "（能量费用是白红蓝绿黑无的记载）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(5)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            if(!isOwnTurn())
            {
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_PAY_ENER, getOpponent(), ChronoDuration.turnEnd(), data -> {
                    return RuleCheckCanPayEner.getDataCostString(data).length() >= 1 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
                });
            }
        }
    }
}
