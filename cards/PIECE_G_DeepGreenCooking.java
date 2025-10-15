package open.batoru.data.cards;

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

public final class PIECE_G_DeepGreenCooking extends Card {

    public PIECE_G_DeepGreenCooking()
    {
        setImageSets("WXDi-P16-006");

        setOriginalName("深緑クッキング");
        setAltNames("シンリョククッキング Shinryoku Kukkingu");
        setDescription("jp",
                "=U あなたの場に緑のルリグがいる\n\n" +
                "あなたの場に緑のルリグが２体以上いるかぎり、このピースの使用コストはあなたの場にいる緑のルリグ１体につき%G減る。\n\n" +
                "あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "Jade Cooking");
        setDescription("en",
                "=U You have a green LRIG on your field.\n\nAs long as there are two or more green LRIG on your field, the use cost of this PIECE is reduced by %G for each green LRIG on your field.\n\nShuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "Deep Green Cooking");
        setDescription("en_fan",
                "=U You have a green LRIG on your field\n\n" +
                "While there are 2 or more green LRIG on your field, the use cost of this piece is reduced by %G for each green LRIG on your field.\n\n" +
                "Shuffle your deck, and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "深绿烹饪");
        setDescription("zh_simplified", 
                "=U你的场上有绿色的分身\n" +
                "你的场上的绿色的分身在2只以上时，这张和音的使用费用依据你的场上的绿色的分身的数量，每有1只就减%G。\n" +
                "你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 3) + Cost.colorless(2));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private CostModifier onPieceEffModCostGetSample()
        {
            int count = new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount();
            return count >= 2 ? new CostModifier(() -> new EnerCost(Cost.color(CardColor.GREEN, count)), ModifierMode.REDUCE) : null;
        }
        private void onPieceEff()
        {
            shuffleDeck();
            addToLifeCloth(1);
        }
    }
}

