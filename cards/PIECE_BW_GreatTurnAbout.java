package open.batoru.data.cards;

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

public final class PIECE_BW_GreatTurnAbout extends Card {
    
    public PIECE_BW_GreatTurnAbout()
    {
        setImageSets("WXDi-P01-003");
        
        setOriginalName("だい！ぎゃく！てん！");
        setAltNames("ダイギャクテン Daigyakuten");
        setDescription("jp",
                "=U あなたの場に白と青のルリグがいる\n\n" +
                "対戦相手の凍結状態のシグニを２体までを対象とし、それらをトラッシュに置く。"
        );
        
        setName("en", "Great! Re - ver - sal!");
        setDescription("en",
                "=U You have a white LRIG and a blue LRIG on your field.\n\n" +
                "Put up to two target frozen SIGNI on your opponent's field into their owner's trash."
        );
        
        setName("en_fan", "Great! Turn! About!");
        setDescription("en_fan",
                "=U There is a white and a blue LRIG on your field\n\n" +
                "Target up to 2 of your opponent's frozen SIGNI, and put them into the trash."
        );
        
		setName("zh_simplified", "大！逆！转！");
        setDescription("zh_simplified", 
                "=U你的场上有白色和蓝色的分身\n" +
                "对战对手的冻结状态的精灵2只最多作为对象，将这些放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE, CardColor.WHITE);
        setCost(Cost.colorless(5));
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
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() == 0 ||
               new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return new TargetFilter().own().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(0,2, new TargetFilter(TargetHint.TRASH).OP().SIGNI().withState(CardStateFlag.FROZEN)));
        }
        private void onPieceEff()
        {
            trash(piece.getTargets());
        }
    }
}
