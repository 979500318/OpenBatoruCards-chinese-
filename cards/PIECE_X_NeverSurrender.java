package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_NeverSurrender extends Card {
    
    public PIECE_X_NeverSurrender()
    {
        setImageSets("WXDi-P01-006");
        
        setOriginalName("ネバー・サレンダー");
        setAltNames("ネバーサレンダー Nebaa Surendaa");
        setDescription("jp",
                "あなたの手札が０枚の場合、あなたのトラッシュから#Gを持たないシグニを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Never Surrender");
        setDescription("en",
                "If you have no cards in your hand, add up to two target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Never Surrender");
        setDescription("en_fan",
                "If there are 0 cards in your hand, target up to 2 SIGNI without #G @[Guard]@ from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "绝不·屈服");
        setDescription("zh_simplified", 
                "你的手牌在0张的场合，从你的废弃区把不持有#G的精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.PIECE);
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
        }
        
        private ConditionState onPieceEffCond()
        {
            return getHandCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getHandCount(getOwner()) == 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
                
                addToHand(data);
            }
        }
    }
}
