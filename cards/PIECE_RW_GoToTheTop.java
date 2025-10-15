package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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

public final class PIECE_RW_GoToTheTop extends Card {
    
    public PIECE_RW_GoToTheTop()
    {
        setImageSets("WXDi-P01-001", "WXDi-D08-011", "SPDi29-01");
        
        setOriginalName("GO TO the TOP!");
        setAltNames("ゴートゥザトップ Goo tu za Toppu");
        setDescription("jp",
                "=U あなたの場に白と赤のルリグがいる\n\n" +
                "あなたのデッキからシグニ１枚を探して公開し手札に加え、デッキをシャッフルする。対戦相手のセンタールリグがレベル３以上の場合、対戦相手は自分のエナゾーンからカードを３枚選びトラッシュに置く。"
        );
        
        setName("en", "Go to the Top!");
        setDescription("en",
                "=U You have a white LRIG and a red LRIG on your field.\n\n" +
                "Search your deck for a SIGNI, reveal it, and add it to your hand. Shuffle your deck after doing so. If your opponent's Center LRIG's level is three or more, they choose and put three cards from their Ener Zone into their trash."
        );
        
        setName("en_fan", "GO TO the TOP!");
        setDescription("en_fan",
                "=U There is a white and a red LRIG on your field\n\n" +
                "Search your deck for 1 SIGNI, reveal it, and add it to your hand, and shuffle your deck. If your opponent's center LRIG is level 3 or higher, your opponent chooses 3 cards from their ener zone and puts them into the trash."
        );
        
		setName("zh_simplified", "GO TO the TOP!");
        setDescription("zh_simplified", 
                "=U你的场上有白色和红色的分身\n" +
                "从你的牌组找精灵1张公开加入手牌，牌组洗切。对战对手的核心分身在等级3以上的场合，对战对手从自己的能量区选3张牌放置到废弃区。（2张以下的场合，将选这些全部）\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.RED, CardColor.WHITE);
        setCost(Cost.colorless(3));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            PieceAbility piece = registerPieceAbility(this::onPieceEffCond, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return (new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0 &&
                    new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            CardIndex cardIndex = searchDeck(new TargetFilter(TargetHint.HAND).own().SIGNI()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            shuffleDeck();
            
            if(getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(3, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            }
        }
    }
}
