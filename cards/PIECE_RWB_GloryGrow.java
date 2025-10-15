package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class PIECE_RWB_GloryGrow extends Card {
    
    public PIECE_RWB_GloryGrow()
    {
        setImageSets("WXDi-D03-011", "PR-Di006");
        
        setOriginalName("Glory Grow");
        setAltNames("グローリーグロウ Guroorii Gurou");
        setDescription("jp",
                "=U =T ＜Ｎｏ　Ｌｉｍｉｔ＞＆全員レベル１以上\n\n" +
                "あなたのレベル３のルリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@C：[[ダブルクラッシュ]]\n" +
                "@C：対戦相手は[[ガード]]ができない。"
        );
        
        setName("en", "Glory Grow");
        setDescription("en",
                "=U You have =T <<No Limit>> on your field with all members level one or more.\n\n" +
                "Target level three LRIG on your field gains the following abilities until end of turn." +
                "@>@C: [[Double Crush]].\n" +
                "@C: Your opponent cannot [[Guard]]."
        );
        
        setName("en_fan", "Glory Grow");
        setDescription("en_fan",
                "=U =T <<No Limit>> and all of them are level 1 or higher\n\n" +
                "Target 1 of your level 3 LRIGs, and until end of turn, it gains:" +
                "@>@C: [[Double Crush]].\n" +
                "@C: Your opponent can't [[Guard]]."
        );
        
		setName("zh_simplified", "Glory Grow");
        setDescription("zh_simplified", 
                "=U=T<<No:Limit>>＆全员等级1以上\n" +
                "你的等级3的分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@C :[[双重击溃]]（攻击给予伤害则把生命护甲2张击溃）\n" +
                "@C 对战对手不能[[防御]]。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED, CardColor.WHITE, CardColor.BLUE);
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
            if(new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() != 3) return ConditionState.BAD;
            
            return new TargetFilter().own().LRIG().withLevel(3).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(3)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                attachAbility(piece.getTarget(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
                
                ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_GUARD, TargetFilter.HINT_OWNER_OP, data -> {
                    return RuleCheckState.BLOCK;
                }));
                attachedConst.setNestedDescriptionOffset(1);
                attachAbility(piece.getTarget(), attachedConst, ChronoDuration.turnEnd());
            }
        }
    }
}
