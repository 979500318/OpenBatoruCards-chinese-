package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_B_SONGOFWIXOSS extends Card {
    
    public PIECE_B_SONGOFWIXOSS()
    {
        setImageSets("WXDi-P03-001");
        
        setOriginalName("SONG OF WIXOSS");
        setAltNames("ソングオブウィクロス Songu obu Uikurosu");
        setDescription("jp",
                "=U あなたの場に青のルリグがいる\n\n" +
                "カードを３枚引く。次のあなたのターン終了時、手札を２枚捨てる。"
        );
        
        setName("en", "SONG OF WIXOSS");
        setDescription("en",
                "=U You have a blue LRIG on your field.\n\n" +
                "Draw three cards. At the beginning of your next end phase, discard two cards."
        );
        
        setName("en_fan", "SONG OF WIXOSS");
        setDescription("en_fan",
                "=U There is a blue LRIG on your field\n\n" +
                "Draw 3 cards. At the next end of your turn, discard 2 cards from your hand."
        );
        
		setName("zh_simplified", "SONG OF WIXOSS");
        setDescription("zh_simplified", 
                "=U你的场上有蓝色的分身\n" +
                "抽3张牌。下一个你的回合结束时，手牌2张舍弃。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            draw(3);
            
            callDelayedEffect(ChronoDuration.nextTurnEnd(getOwner()), () -> {
                discard(2);
            });
        }
    }
}
