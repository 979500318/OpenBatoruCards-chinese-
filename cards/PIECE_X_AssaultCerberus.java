package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
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

public final class PIECE_X_AssaultCerberus extends Card {
    
    public PIECE_X_AssaultCerberus()
    {
        setImageSets("WXDi-P00-006");
        
        setOriginalName("アサルト・ケルベロス");
        setAltNames("アサルトケルベロス Asaruto Keruberosu");
        setDescription("jp",
                "対戦相手の手札を見て#Gを持たないカードを１枚選び、捨てさせる。"
        );
        
        setName("en", "Assault Cerberus");
        setDescription("en",
                "Look at your opponent's hand, choose a card without a #G. Your opponent discards it."
        );
        
        setName("en_fan", "Assault Cerberus");
        setDescription("en_fan",
                "Look at your opponent's hand, choose 1 card without #G @[Guard]@, and your opponent discards it."
        );
        
		setName("zh_simplified", "突袭·地狱犬");
        setDescription("zh_simplified", 
                "看对战对手的手牌选不持有#G的牌1张，舍弃。\n"
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
            return getHandCount(getOpponent()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}
