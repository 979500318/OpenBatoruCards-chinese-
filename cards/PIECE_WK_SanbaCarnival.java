package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_WK_SanbaCarnival extends Card {
    
    public PIECE_WK_SanbaCarnival()
    {
        setImageSets("WXDi-D02-19LAT");
        
        setOriginalName("サンバ・カーニバル");
        setAltNames("サンバカーニバル Sanba Kaanibaru");
        setDescription("jp",
                "=U =T ＜さんばか＞＆全員レベル１以上\n\n" +
                "対戦相手のシグニ１体を対象とし、それをトラッシュに置く。対戦相手のシグニ１体を対象とし、それを手札に戻す。あなたのトラッシュから＜バーチャル＞のシグニ１体を対象とし、それを手札に加える。"
        );
        
        setName("en", "Samba Carnival");
        setDescription("en",
                "=U You have =T <<Sanbaka>> on your field with all members level one or more.\n\n" +
                "Put target SIGNI on your opponent's field into its owner's trash. Return target SIGNI on your opponent's field to its owner's hand. Add target <<Virtual>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Sanba Carnival");
        setDescription("en_fan",
                "=U =T <<Sanbaka>> and all of them are level 1 or higher\n\n" +
                "Target 1 of your opponent's SIGNI, and put it into the trash. Target 1 of your opponent's SIGNI, and return it to their hand. Target 1 <<Virtual>> SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "三笨蛋·嘉年华");
        setDescription("zh_simplified", 
                "=U=T<<さんばか>>&amp;全员等级1以上\n" +
                "对战对手的精灵1只作为对象，将其放置到废弃区。对战对手的精灵1只作为对象，将其返回手牌。从你的废弃区把<<バーチャル>>精灵1只作为对象，将其加入手牌。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE, CardColor.BLACK);
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
            return new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() == 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            trash(piece.getTarget());
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash()).get();
            addToHand(target);
        }
    }
}
