package open.batoru.data.cards;

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

public final class PIECE_KG_NightmareStep extends Card {
    
    public PIECE_KG_NightmareStep()
    {
        setImageSets("WXDi-P01-005");
        
        setOriginalName("NIGHTMARE■STEP");
        setAltNames("ナイトメアステップ Naitomea Suteppu");
        setDescription("jp",
                "=U あなたの場に緑と黒のルリグがいる\n\n" +
                "あなたのトラッシュにカードが２５枚以上ある場合、対戦相手のすべてのシグニをエナゾーンに置く。"
        );
        
        setName("en", "Nightmare■Step");
        setDescription("en",
                "=U You have a green LRIG and a black LRIG on your field.\n\n" +
                "If you have twenty five or more cards in your trash, put all SIGNI on your opponent's field into their Ener Zone."
        );
        
        setName("en_fan", "NIGHTMARE■STEP");
        setDescription("en_fan",
                "=U There is a green and a black LRIG on your field\n\n" +
                "If there are 25 or more cards in your trash, put all of your opponent's SIGNI from the field into the ener zone."
        );
        
		setName("zh_simplified", "NIGHTMARE■STEP");
        setDescription("zh_simplified", 
                "=U你的场上有绿色和黑色的分身\n" +
                "你的废弃区的牌在25张以上的场合，对战对手的全部的精灵放置到能量区。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLACK, CardColor.GREEN);
        setCost(Cost.colorless(6));
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
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() == 0 ||
               new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getTrashCount(getOwner()) >= 25 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getTrashCount(getOwner()) >= 25)
            {
                putInEner(getSIGNIOnField(getOpponent()));
            }
        }
    }
}
