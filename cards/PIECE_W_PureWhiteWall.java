package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;

public final class PIECE_W_PureWhiteWall extends Card {

    public PIECE_W_PureWhiteWall()
    {
        setImageSets("WXDi-P16-003");
        setLinkedImageSets(Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("純白の防壁");
        setAltNames("ジュンパクノボウヘキ Junpaku no Bouheki");
        setDescription("jp",
                "=U あなたの場に白のルリグがいる\n\n" +
                "あなたの場に白のルリグが２体以上いるかぎり、このピースの使用コストはあなたの場にいる白のルリグ１体につき%W減る。\n\n" +
                "【ルリグバリア】２つを得る。"
        );

        setName("en", "Pure White Barrier");
        setDescription("en",
                "=U You have a white LRIG on your field.\n\nAs long as there are two or more white LRIG on your field, the use cost of this PIECE is reduced by %W for each white LRIG on your field.\n\nGain two [[LRIG Barrier]]. \n"
        );
        
        setName("en_fan", "Pure White Wall");
        setDescription("en_fan",
                "=U You have a white LRIG on your field\n\n" +
                "While there are 2 or more white LRIG on your field, the use cost of this piece is reduced by %W for each white LRIG on your field.\n\n" +
                "Gain 2 [[LRIG Barrier]]."
        );

		setName("zh_simplified", "纯白的防壁");
        setDescription("zh_simplified", 
                "=U你的场上有白色的分身\n" +
                "你的场上的白色的分身在2只以上时，这张和音的使用费用依据你的场上的白色的分身的数量，每有1只就减%W。\n" +
                "得到[[分身屏障]]2个。（你下一次从分身受到伤害的场合，作为替代，消费[[分身屏障]]1个，不会受到那次伤害）\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 3) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setCostModifier(this::onPieceEffModCostGetSample);
        }
        
        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private CostModifier onPieceEffModCostGetSample()
        {
            int count = new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount();
            return count >= 2 ? new CostModifier(() -> new EnerCost(Cost.color(CardColor.WHITE, count)), ModifierMode.REDUCE) : null;
        }
        private void onPieceEff()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
        }
    }
}

