package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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

public final class PIECE_W_WhiteHeaven extends Card {
    
    public PIECE_W_WhiteHeaven()
    {
        setImageSets("WXDi-P04-001", "WXDi-CP01-053");
        
        setOriginalName("ホワイトヘブン");
        setAltNames("Howaito Hebun");
        setDescription("jp",
                "=U あなたの場に白のルリグが２体上いる\n\n" +
                "対戦相手のシグニ１体を対象とし、それをトラッシュに置く。対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "White Heaven");
        setDescription("en",
                "=U You have two or more white LRIG on your field.\n\n" +
                "Put target SIGNI on your opponent's field into its owner's trash. Return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "White Heaven");
        setDescription("en_fan",
                "=U There are 2 or more white LRIGs on your field\n\n" +
                "Target 1 of your opponent's SIGNI, and put it into the trash. Target 1 of your opponent's SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "纯白天堂");
        setDescription("zh_simplified", 
                "=U你的场上的白色的分身在2只以上\n" +
                "对战对手的精灵1只作为对象，将其放置到废弃区。对战对手的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1));
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
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() < 2) return ConditionState.BAD;
            
            return getSIGNICount(getOwner()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            trash(piece.getTarget());
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
    }
}
