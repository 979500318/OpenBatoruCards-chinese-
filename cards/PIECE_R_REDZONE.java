package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
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
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class PIECE_R_REDZONE extends Card {
    
    public PIECE_R_REDZONE()
    {
        setImageSets("WXDi-P05-001");
        
        setOriginalName("RED ZONE");
        setAltNames("レッドゾーン Reddo Zoon");
        setDescription("jp",
                "=U あなたの場に赤のルリグが２体以上いる\n\n" +
                "あなたの赤のシグニ１体を対象とし、ターン終了時まで、それは[[アサシン]]を得る。手札を３枚まで捨て、この方法で捨てた枚数に２を加えた枚数のカードを引く。"
        );
        
        setName("en", "RED ZONE");
        setDescription("en",
                "=U You have two or more red LRIG on your field.\n\n" +
                "Target red SIGNI on your field gains [[Assassin]] until end of turn. Discard up to three cards and draw cards equal to the number of cards you discarded plus two."
        );
        
        setName("en_fan", "RED ZONE");
        setDescription("en_fan",
                "=U There are 2 or more red LRIGs on your field\n\n" +
                "Target 1 of your red SIGNI, and until end of turn, it gains [[Assassin]]. Discard up to 3 cards from your hand, and draw cards equal to the number of cards discarded this way plus 2."
        );
        
		setName("zh_simplified", "RED ZONE");
        setDescription("zh_simplified", 
                "=U你的场上的红色的分身在2只以上\n" +
                "你的红色的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。手牌3张最多舍弃，抽这个方法舍弃的张数加2的张数的牌。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null) attachAbility(piece.getTarget(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            
            DataTable<CardIndex> data = discard(0,3);
            draw((data.get() != null ? data.size() : 0) + 2);
        }
    }
}
