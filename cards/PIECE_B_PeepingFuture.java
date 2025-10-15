package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CostRuleCheck;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class PIECE_B_PeepingFuture extends Card {
    
    public PIECE_B_PeepingFuture()
    {
        setImageSets("WXDi-P06-002");
        
        setOriginalName("ピーピング・フューチャー");
        setAltNames("ピーピングフューチャー Piipingu Fyuuchaa");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたのレベル３のルリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@U $T1：このルリグがアタックしたとき、対戦相手の手札を見て１枚選び、捨てさせる。次の対戦相手のアップフェイズに、対戦相手が%X %X %Xを支払わないかぎり、対戦相手のセンタールリグはアップしない。"
        );
        
        setName("en", "Peeping Future");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Target level three LRIG on your field gains the following ability until end of turn." +
                "@>@U $T1: When this LRIG attacks, look at your opponent's hand and choose a card. Your opponent discards it. During your opponent's next up phase, your opponent cannot up their Center LRIG unless they pay %X %X %X."
        );
        
        setName("en_fan", "Peeping Future");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Target 1 of your level 3 LRIG, and until end of turn, it gains:" +
                "@>@U $T1: When this LRIG attacks, look at your opponent's hand, choose 1 card, and discard it. During your opponent's next up phase, your opponent's center LRIG doesn't up unless they pay %X %X %X."
        );
        
		setName("zh_simplified", "窥视·未来");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的等级3的分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@U $T1 :当这只分身攻击时，看对战对手的手牌选1张，舍弃。下一个对战对手的竖直阶段，如果对战对手不把%X %X %X支付，那么对战对手的核心分身不能竖直。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            if(CardAbilities.getColorsCount(getLRIGs(getOwner())) < 3) return ConditionState.BAD;
            
            return new TargetFilter().own().LRIG().withLevel(3).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter().own().LRIG().withLevel(3)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(piece.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
            
            addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_UP, getOpponent(), ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.UP), data -> {
                return CostRuleCheck.getCardIndex(data) == getLRIG(getOpponent()) ? new EnerCost(Cost.colorless(3)) : null;
            });
        }
    }
}
