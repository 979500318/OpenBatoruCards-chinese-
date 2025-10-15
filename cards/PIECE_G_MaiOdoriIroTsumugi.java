package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class PIECE_G_MaiOdoriIroTsumugi extends Card {
    
    public PIECE_G_MaiOdoriIroTsumugi()
    {
        setImageSets("WXDi-P08-004");
        
        setOriginalName("舞イ踊リ色紬");
        setAltNames("マイオドリイロツムギ Mai Odori Iro Tsumugi");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたの場に白のルリグがいる場合、対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "その後、あなたの場に赤のルリグがいる場合、あなたのレベル３以上のシグニ１体を対象とし、ターン終了時まで、それは【アサシン】を得る。\n" +
                "その後、あなたの場に青のルリグがいる場合、対戦相手のルリグ１体を対象とし、それを凍結する。"
        );
        
        setName("en", "Colorful Ensemble");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "If there is a white LRIG on your field, return target SIGNI on your opponent's field to its owner's hand.\n" +
                "Then, if there is a red LRIG on your field, target level three or more SIGNI on your field gains [[Assassin]] until end of turn.\n" +
                "Then, if there is a blue LRIG on your field, freeze target LRIG on your opponent's field."
        );
        
        setName("en_fan", "Mai Odori Iro Tsumugi");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If there is a white LRIG on your field, target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "Then, if there is a red LRIG on your field, target 1 of your level 3 or higher SIGNI, and until end of turn, it gains [[Assassin]].\n" +
                "Then, if there is a blue LRIG on your field, target 1 of your opponent's LRIG, and freeze it."
        );
        
		setName("zh_simplified", "舞踊色紬");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的场上有白色的分身的场合，对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "然后，你的场上有红色的分身的场合，你的等级3以上的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。\n" +
                "然后，你的场上有蓝色的分身的场合，对战对手的分身1只作为对象，将其冻结。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(3,0)).get();
                if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
                freeze(target);
            }
        }
    }
}
