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

public final class PIECE_X_TapDownTap extends Card {
    
    public PIECE_X_TapDownTap()
    {
        setImageSets("WXDi-P01-007");
        
        setOriginalName("●TAP DOWN TAP●");
        setAltNames("タップダウンタップ Tappu Daun Tappu");
        setDescription("jp",
                "あなたのライフクロスが０枚の場合、対戦相手のルリグかシグニ１体を対象とし、それをダウンする。"
        );
        
        setName("en", "●Tap Down Tap●");
        setDescription("en",
                "If you have no cards in your Life Cloth, down target LRIG or SIGNI on your opponent's field."
        );
        
        setName("en_fan", "●TAP DOWN TAP●");
        setDescription("en_fan",
                "If you have 0 life cloth, target 1 of your opponent's LRIGs or SIGNI, and down it."
        );
        
		setName("zh_simplified", "●TAP DOWN TAP●");
        setDescription("zh_simplified", 
                "你的生命护甲在0张的场合，对战对手的分身或精灵1只作为对象，将其横置。\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);
        
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
            return getLifeClothCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getLifeClothCount(getOwner()) == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().fromField()).get();
                down(target);
            }
        }
    }
}
