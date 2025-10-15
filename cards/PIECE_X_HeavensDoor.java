package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_HeavensDoor extends Card {
    
    public PIECE_X_HeavensDoor()
    {
        setImageSets("WXDi-P02-004");
        
        setOriginalName("Heaven's Door");
        setAltNames("ヘブンズドアー Hebunzu Doaa");
        setDescription("jp",
                "=U あなたのトラッシュに＜天使＞のシグニが７種類以上ある\n\n" +
                "対戦相手のシグニ１体を対象とし、それをトラッシュに置く。カードを２枚引く。"
        );
        
        setName("en", "Heaven's Door");
        setDescription("en",
                "=U You have seven or more different <<Angel>> SIGNI in your trash.\n\n" +
                "Put target SIGNI on your opponent's field into its owner's trash. Draw two cards."
        );
        
        setName("en_fan", "Heaven's Door");
        setDescription("en_fan",
                "=U There are 7 or more types of <<Angel>> SIGNI in your trash\n\n" +
                "Target 1 of your opponent's SIGNI, and put it into the trash. Draw 2 cards."
        );
        
		setName("zh_simplified", "Heaven’s Door");
        setDescription("zh_simplified", 
                "=U你的废弃区的<<天使>>精灵在7种类以上\n" +
                "对战对手的精灵1只作为对象，将其放置到废弃区。抽2张牌。\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(3));
        setUseTiming(UseTiming.MAIN);
        
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
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().
                    getExportedData().stream().map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 7 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            trash(piece.getTarget());
            
            draw(2);
        }
    }
}
