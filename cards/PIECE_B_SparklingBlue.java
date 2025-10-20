package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
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

public final class PIECE_B_SparklingBlue extends Card {

    public PIECE_B_SparklingBlue()
    {
        setImageSets("WXDi-P16-005");

        setOriginalName("キラキラ・ブルー");
        setAltNames("キラキラブルー Kirakira Buruu");
        setDescription("jp",
                "=U あなたの場に青のルリグがいる\n\n" +
                "あなたの場に青のルリグが２体以上いるかぎり、このピースの使用コストはあなたの場にいる青のルリグ１体につき%B減る。\n\n" +
                "対戦相手のすべてのルリグとシグニをダウンし凍結する。次の対戦相手のドローフェイズの間、対戦相手はカードを合計１枚までしか引けない。"
        );

        setName("en", "Sparkling Blue");
        setDescription("en",
                "=U You have a blue LRIG on your field.\n\nAs long as there are two or more blue LRIG on your field, the use cost of this PIECE is reduced by %B for each blue LRIG on your field.\n\nDown all LRIG and SIGNI on your opponent's field and freeze them. During your opponent's next draw phase, they can only draw one card in total."
        );
        
        setName("en_fan", "Sparkling Blue");
        setDescription("en_fan",
                "=U You have a blue LRIG on your field\n\n" +
                "While there are 2 or more blue LRIG on your field, the use cost of this piece is reduced by %B for each blue LRIG on your field.\n\n" +
                "Down and freeze all of your opponent's LRIG and SIGNI. During your opponent's next draw phase, your opponent can only draw up to 1 card in total."
        );

		setName("zh_simplified", "闪闪·蔚蓝");
        setDescription("zh_simplified", 
                "=U你的场上有蓝色的分身\n" +
                "你的场上的蓝色的分身在2只以上时，这张和音的使用费用依据你的场上的蓝色的分身的数量，每有1只就减%B。\n" +
                "对战对手的全部的分身和精灵横置并冻结。下一个对战对手的抽牌阶段期间，对战对手只能抽合计1张最多的牌。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 3) + Cost.colorless(1));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private CostModifier onPieceEffModCostGetSample()
        {
            int count = new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount();
            return count >= 2 ? new CostModifier(() -> new EnerCost(Cost.color(CardColor.BLUE, count)), ModifierMode.REDUCE) : null;
        }
        private void onPieceEff()
        {
            down(getSIGNIOnField(getOpponent()));
            freeze(getSIGNIOnField(getOpponent()));
            down(getLRIGs(getOpponent()));
            freeze(getLRIGs(getOpponent()));
            
            setPlayerRuleValue(getOpponent(), PlayerRuleValueType.DRAW_PHASE_MAX, 1, ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.DRAW));
        }
    }
}

