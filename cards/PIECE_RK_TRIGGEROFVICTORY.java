package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_RK_TRIGGEROFVICTORY extends Card {
    
    public PIECE_RK_TRIGGEROFVICTORY()
    {
        setImageSets("WXDi-D07-011", "PR-Di022");
        
        setOriginalName("TRIGGER OF VICTORY");
        setAltNames("トリガーオブビクトリー Torigaa obu Bikutorii");
        setDescription("jp",
                "=U =T ＜デウス・エクス・マキナ＞＆全員レベル１以上\n\n" +
                "対戦相手のライフクロス１枚をクラッシュする。その後、あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "TRIGGER OF VICTORY");
        setDescription("en",
                "=U You have =T <<Deus Ex Machina>> on your field with all members level one or more.\n\n" +
                "Crush one of your opponent's Life Cloth. Then, add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "TRIGGER OF VICTORY");
        setDescription("en_fan",
                "=U =T <<Deus Ex Machina>> and all of them are level 1 or higher\n\n" +
                "Crush 1 of your opponent's life cloth. Then, target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "TRIGGER OF VICTORY");
        setDescription("zh_simplified", 
                "=U=T<<デウス・エクス・マキナ>>&amp;全员等级1以上\n" +
                "对战对手的生命护甲1张击溃。然后，从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED, CardColor.BLACK);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);
        
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
            return new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() == 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            crush(getOpponent());
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
