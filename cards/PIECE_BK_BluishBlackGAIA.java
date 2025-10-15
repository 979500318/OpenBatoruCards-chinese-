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
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

import java.util.List;

public final class PIECE_BK_BluishBlackGAIA extends Card {
    
    public PIECE_BK_BluishBlackGAIA()
    {
        setImageSets("WXDi-P02-003");
        
        setOriginalName("蒼黒ＧＡＩＡ");
        setAltNames("ソウコクガイア Soukoku Gaia");
        setDescription("jp",
                "=U あなたの場に青と黒のルリグがいる\n\n" +
                "対戦相手の手札を２枚見ないで選び、捨てさせる。その後、あなたのトラッシュから#Gを持たないシグニを、レベルの合計がこの方法で捨てられたシグニのレベルの合計と等しくなるように２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Azure Black GAIA");
        setDescription("en",
                "=U You have a blue LRIG and a black LRIG on your field.\n\n" +
                "Your opponent discards two cards at random. Then, add up to two target SIGNI without a #G from your trash to your hand whose total level is equal to the total level of the SIGNI discarded this way."
        );
        
        setName("en_fan", "Bluish Black GAIA");
        setDescription("en_fan",
                "=U There is a blue and a black LRIG on your field\n\n" +
                "Choose 2 cards from your opponent's hand without looking, and discard them. Target up to 2 SIGNI from your trash without #G @[Guard]@ whose total level is equal to the total level of the SIGNI discarded this way, and add them to your hand."
        );
        
		setName("zh_simplified", "苍黑GAIA");
        setDescription("zh_simplified", 
                "=U你的场上有蓝色和黑色的分身\n" +
                "不看对战对手的手牌选2张，舍弃。然后，从你的废弃区把不持有#G的精灵，等级的合计在与这个方法舍弃的精灵的等级的合计相等的2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setCost(Cost.colorless(3));
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
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() == 0 ||
               new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getHandCount(getOpponent()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        int sumLevels;
        private void onPieceEff()
        {
            DataTable<CardIndex> data = playerChoiceHand(2);
            discard(data);
            
            if(data.get() != null)
            {
                sumLevels = 0;
                for(int i=0;i<data.size();i++) sumLevels += data.get(i).getIndexedInstance().getLevel().getValue();
                
                data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash(), this::onPieceEffTargetCond);
                addToHand(data);
            }
        }
        private boolean onPieceEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).sum() == sumLevels;
        }
    }
}
