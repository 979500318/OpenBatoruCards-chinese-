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

public final class PIECE_WK_SuperHelestaSaber extends Card {
    
    public PIECE_WK_SuperHelestaSaber()
    {
        setImageSets("WXDi-P00-001");
        
        setOriginalName("サンバ・カーニバル");
        setAltNames("サンバカーニバル Sanba Kaanibaru");
        setDescription("jp",
                "=U あなたの場に白と黒のルリグがいる\n\n" +
                "あなたのトラッシュから白と黒と無色のシグニをそれぞれ１枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Super Helesta Saber");
        setDescription("en",
                "=U You have a white LRIG and a black LRIG on your field\n\n" +
                "Add up to one target white SIGNI, one target black SIGNI, and one target colorless SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Super Helesta Saber");
        setDescription("en_fan",
                "=U There is a white and a black LRIG on your field\n\n" +
                "Target up to 1 white, black and colorless SIGNI in your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "超级·赫露艾斯塔皇剑");
        setDescription("zh_simplified", 
                "=U你的场上有白色和黑色的分身\n" +
                "从你的废弃区把白色和黑色和无色的精灵各1张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return (new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0 &&
                    new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() > 0) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            DataTable<CardIndex> data = new DataTable<>();
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.WHITE).fromTrash()).get();
            data.add(target);
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).except(data).fromTrash()).get();
            data.add(target);
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withColor()).fromTrash()).get();
            data.add(target);
            
            piece.setTargets(data);
        }
        private void onPieceEff()
        {
            addToHand(piece.getTargets());
        }
    }
}
