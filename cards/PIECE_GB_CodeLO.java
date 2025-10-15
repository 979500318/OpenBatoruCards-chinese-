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

public final class PIECE_GB_CodeLO extends Card {
    
    public PIECE_GB_CodeLO()
    {
        setImageSets("WXDi-P01-004");
        
        setOriginalName("code:L/O");
        setAltNames("コードエルオー Koodo Eru Oo");
        setDescription("jp",
                "=U あなたの場に青と緑のルリグがいる\n\n" +
                "あなたのライフクロスが０枚の場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );
        
        setName("en", "Code: L/O");
        setDescription("en",
                "=U You have a blue LRIG and a green LRIG on your field.\n\n" +
                "If you have no cards in your Life Cloth, shuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "code:L/O");
        setDescription("en_fan",
                "=U There is a blue and a green LRIG on your field\n\n" +
                "If you have 0 life cloth, shuffle your deck and add the top card of your deck to life cloth."
        );
        
		setName("zh_simplified", "code: L/O");
        setDescription("zh_simplified", 
                "=U你的场上有蓝色和绿色的分身\n" +
                "你的生命护甲在0张的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.GREEN, CardColor.BLUE);
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
               new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getLifeClothCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getLifeClothCount(getOwner()) == 0)
            {
                shuffleDeck();
                
                addToLifeCloth(1);
            }
        }
    }
}
