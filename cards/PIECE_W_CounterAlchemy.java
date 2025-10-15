package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class PIECE_W_CounterAlchemy extends Card {
    
    public PIECE_W_CounterAlchemy()
    {
        setImageSets("WXDi-P00-002");
        
        setOriginalName("カウンター・アルケミー");
        setAltNames("カウンターアルケミー Kauntaa Arukemii");
        setDescription("jp",
                "=U あなたの場に白のルリグがいる\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それは[[アサシン]][[ランサー]][[ダブルクラッシュ]]を失い、新たに得られない。"
        );
        
        setName("en", "Counter Alchemy");
        setDescription("en",
                "=U You have a white LRIG on your field\n\n" +
                "Until end of turn, target SIGNI on your opponent's field loses [[Assassin]], [[Lancer]], and [[Double Crush]], and cannot regain those abilities."
        );
        
        setName("en_fan", "Counter Alchemy");
        setDescription("en_fan",
                "=U There is a white LRIG on your field\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, that SIGNI loses [[Assassin]], [[Lancer]], and [[Double Crush]], and can't newly gain them."
        );
        
		setName("zh_simplified", "逆向·炼金");
        setDescription("zh_simplified", 
                "=U你的场上有白色的分身\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，将其的[[暗杀]][[枪兵]][[双重击溃]]失去，不能新得到。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getSIGNICount(getOpponent()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                addCardRuleCheck(CardRuleCheckType.CAN_ABILITY_BE_ATTACHED, piece.getTarget(), ChronoDuration.turnEnd(), data -> {
                    return data.getSourceAbility().getSourceStockAbility() != null &&
                           (data.getSourceAbility().getSourceStockAbility() instanceof StockAbilityAssassin ||
                            data.getSourceAbility().getSourceStockAbility() instanceof StockAbilityLancer ||
                            data.getSourceAbility().getSourceStockAbility() instanceof StockAbilityDoubleCrush) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
                });
                
                forEachAbility(piece.getTarget(), ability -> {
                    if(ability.getSourceStockAbility() instanceof StockAbilityAssassin ||
                       ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                       ability.getSourceStockAbility() instanceof StockAbilityDoubleCrush)
                    {
                        ability.disable(ChronoDuration.turnEnd());
                    }
                });
            }
        }
    }
}
