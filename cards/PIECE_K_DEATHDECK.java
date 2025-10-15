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

public final class PIECE_K_DEATHDECK extends Card {
    
    public PIECE_K_DEATHDECK()
    {
        setImageSets("WXDi-P04-004", "WXDi-CP01-054");
        
        setOriginalName("DEATH DECK");
        setAltNames("デスデッキ Desu Dekki");
        setDescription("jp",
                "=U あなたの場に黒のルリグが２体上いる\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。その後、以下の２つからあなたのトラッシュにあるカード１０枚につき１つを選ぶ。\n" +
                "$$1あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニを２枚まで対象とし、それらを手札に加える。\n" +
                "$$2対戦相手のデッキの上からカードを１０枚トラッシュに置く。"
        );
        
        setName("en", "DEATH DECK");
        setDescription("en",
                "=U You have two or more black LRIG on your field.\n\n" +
                "Target SIGNI on your opponent's field gets --12000 power until end of turn. Then, choose one of the following for every ten cards in your trash.\n" +
                "$$1 Add up to two target SIGNI that share a color with your center LRIG from your trash to your hand.\n" +
                "$$2 Put the top ten cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "DEATH DECK");
        setDescription("en_fan",
                "=U There are 2 or more black LRIGs on your field\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power. Then, for every 10 cards in your trash, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 SIGNI that share a common color with your center LRIG from your trash, and add them to your hand.\n" +
                "$$2 Put the top 10 cards of your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "DEATH DECK");
        setDescription("zh_simplified", 
                "=U你的场上的黑色的分身在2只以上\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。然后，从以下的2种依据你的废弃区的牌的数量，每有10张就选1种。\n" +
                "$$1 从你的废弃区把持有与你的核心分身共通颜色的精灵2张最多作为对象，将这些加入手牌。\n" +
                "$$2 从对战对手的牌组上面把10张牌放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2) + Cost.colorless(1));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            gainPower(piece.getTarget(), -12000, ChronoDuration.turnEnd());
            
            int count = Math.min(2, getTrashCount(getOwner()) / 10);
            if(count > 0)
            {
                int modes = playerChoiceMode(count,count);
                
                if((modes & 1) != 0)
                {
                    DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash());
                    addToHand(data);
                }
                if((modes & 2) != 0)
                {
                    millDeck(getOpponent(), 10);
                }
            }
        }
    }
}
