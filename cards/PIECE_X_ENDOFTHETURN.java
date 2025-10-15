package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_ENDOFTHETURN extends Card {
    
    public PIECE_X_ENDOFTHETURN()
    {
        setImageSets("WXDi-P02-006");
        
        setOriginalName("END OF THE TURN");
        setAltNames("エンドオブザターン Endo obu za Taan");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1コストの合計が３以下のスペル１つを対象とし、それの効果を打ち消す。\n" +
                "$$2あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "End of the Turn");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Counter the effect of target spell whose combined cost is three or less.\n" +
                "$$2 Add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "END OF THE TURN");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 spell with total cost of 3 or less, and cancel it.\n" +
                "$$2 Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "END OF THE TURN");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 费用的合计在3以下的魔法1张作为对象，将其的效果取消。\n" +
                "（费用的合计是，牌的左上的能量费用的数字的合计。例费用是%W%X %X:的场合，费用的合计是3）\n" +
                "$$2 从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN | UseTiming.SPELLCUTIN);
        
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
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setModeChoice(1);
        }
        
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.CANCEL).spell().withCost(0,3)).get();
                cancel(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                addToHand(target);
            }
        }
    }
}
