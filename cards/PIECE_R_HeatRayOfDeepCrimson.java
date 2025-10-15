package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
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

public final class PIECE_R_HeatRayOfDeepCrimson extends Card {

    public PIECE_R_HeatRayOfDeepCrimson()
    {
        setImageSets("WXDi-P16-004");

        setOriginalName("真紅の熱線");
        setAltNames("シンクノネッセン Shinku no Nessen");
        setDescription("jp",
                "=U あなたの場に赤のルリグがいる\n\n" +
                "あなたの場に赤のルリグが２体以上いるかぎり、このピースの使用コストはあなたの場にいる赤のルリグ１体につき%R減る。\n\n" +
                "対戦相手のライフクロス１枚をトラッシュに置く。"
        );

        setName("en", "Crimson Heat Ray");
        setDescription("en",
                "=U You have a red LRIG on your field.\n\nAs long as there are two or more red LRIG on your field, the use cost of this PIECE is reduced by %R for each red LRIG on your field.\n\nPut one of your opponent's Life Cloth into their trash."
        );
        
        setName("en_fan", "Heat Ray of Deep Crimson");
        setDescription("en_fan",
                "=U You have a red LRIG on your field\n\n" +
                "While there are 2 or more red LRIG on your field, the use cost of this piece is reduced by %R for each red LRIG on your field.\n\n" +
                "Put 1 of your opponent's life cloth into the trash."
        );

		setName("zh_simplified", "真红的热线");
        setDescription("zh_simplified", 
                "=U你的场上有红色的分身\n" +
                "你的场上的红色的分身在2只以上时，这张和音的使用费用依据你的场上的红色的分身的数量，每有1只就减%R。\n" +
                "对战对手的生命护甲1张放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 3) + Cost.colorless(2));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private CostModifier onPieceEffModCostGetSample()
        {
            int count = new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount();
            return count >= 2 ? new CostModifier(() -> new EnerCost(Cost.color(CardColor.RED, count)), ModifierMode.REDUCE) : null;
        }
        private void onPieceEff()
        {
            trash(getOpponent(), CardLocation.LIFE_CLOTH);
        }
    }
}

