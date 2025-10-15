package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class PIECE_X_BurningCuriosity extends Card {
    
    public PIECE_X_BurningCuriosity()
    {
        setImageSets("WXDi-P03-006", "WXDi-D08-012");
        
        setOriginalName("burning curiosity");
        setAltNames("バーニングキュリオシティ Baaningu Kyurioshiti");
        setDescription("jp",
                "このピースを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "対戦相手のシグニ１体を対象とし、それをバニッシュする。追加でエクシード４を支払っていた場合、代わりにそれをゲームから除外する。"
        );
        
        setName("en", "Burning Curiosity");
        setDescription("en",
                "As you use this PIECE, you may pay Exceed 4 as an additional use cost. \n\n" +
                "Vanish target SIGNI on your opponent's field. If you paid the Exceed 4, instead remove it from the game."
        );
        
        setName("en_fan", "burning curiosity");
        setDescription("en_fan",
                "While using this piece, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Target 1 of your opponent's SIGNI, and banish it. If you additionally paid @[Exceed 4]@, exclude it from the game instead."
        );
        
		setName("zh_simplified", "burning curiosity");
        setDescription("zh_simplified", 
                "这张和音使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "对战对手的精灵1只作为对象，将其破坏。追加把@[超越 4]@支付过的场合，作为替代，将其从游戏除外。\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
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
            piece.setAdditionalCost(new ExceedCost(4));
        }
        
        private ConditionState onPieceEffCond()
        {
            return getSIGNICount(getOpponent()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(!piece.hasPaidAdditionalCost() ? TargetHint.BANISH : TargetHint.EXCLUDE).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            if(!piece.hasPaidAdditionalCost())
            {
                banish(piece.getTarget());
            } else {
                exclude(piece.getTarget());
            }
        }
    }
}
