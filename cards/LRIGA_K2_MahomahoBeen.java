package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_K2_MahomahoBeen extends Card {
    
    public LRIGA_K2_MahomahoBeen()
    {
        setImageSets("WXDi-P05-031");
        
        setOriginalName("まほまほ☆べーん");
        setAltNames("マホマホベーン Mahomaho Been");
        setDescription("jp",
                "@E：このターン、対戦相手はパワーが10000以下のシグニでアタックできない。\n" +
                "@E @[手札を３枚まで捨てる]@：この方法で捨てたカードの枚数と同じ数の対戦相手のシグニを対象とし、ターン終了時まで、それらのパワーをそれぞれ－5000する。"
        );
        
        setName("en", "Mahomaho☆Been");
        setDescription("en",
                "@E: Your opponent cannot attack with SIGNI with power 10000 or less this turn.\n" +
                "@E @[Discard up to three cards]@: The same number of target SIGNI on your opponent's field equal to the number of cards discarded this way get --5000 power until end of turn."
        );
        
        setName("en_fan", "Mahomaho☆Been");
        setDescription("en_fan",
                "@E: This turn, your opponent can't attack with SIGNI with power 10000 or less.\n" +
                "@E @[Discard up to 3 cards from your hand]@: Target the same number of your opponent's SIGNI as the number of cards discarded this way, and until end of turn, they get --5000 power."
        );
        
		setName("zh_simplified", "真帆帆☆叮");
        setDescription("zh_simplified", 
                "@E :这个回合，对战对手的力量在10000以下的精灵不能攻击。\n" +
                "@E 手牌3张最多舍弃:与这个方法舍弃的牌的张数相同数量的对战对手的精灵作为对象，直到回合结束时为止，这些的力量各-5000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
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
            registerEnterAbility(new DiscardCost(0,3), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_ATTACK, getOpponent(), ChronoDuration.turnEnd(), data -> {
                return CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                       data.getSourceCardIndex().getIndexedInstance().getPower().getValue() <= 10000 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
            });
        }
        
        private void onEnterEff2()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                DataTable<CardIndex> data = playerTargetCard(getAbility().getCostPaidData().size(), new TargetFilter(TargetHint.MINUS).OP().SIGNI());
                gainPower(data, -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
