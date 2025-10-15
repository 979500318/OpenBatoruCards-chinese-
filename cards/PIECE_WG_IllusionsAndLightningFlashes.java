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
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class PIECE_WG_IllusionsAndLightningFlashes extends Card {
    
    public PIECE_WG_IllusionsAndLightningFlashes()
    {
        setImageSets("WXDi-P02-001");
        
        setOriginalName("幻影と稲光");
        setAltNames("ゲンエイトイナビカリ Genei to Inabikari");
        setDescription("jp",
                "=U あなたの場に白と緑のルリグがいる\n\n" +
                "@[@|以下の２つから１つを選ぶ。|@]@\n" +
                "$$1 あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。\n" +
                "$$2 対戦相手のターンの場合、あなたのシグニ１体を対象とし、ターン終了時まで、それは[[シャドウ]]を得る。"
        );
        
        setName("en", "Illusions and Lightning");
        setDescription("en",
                "=U You have a white LRIG and a green LRIG on your field.\n\n" +
                "Choose one of the following.\n" +
                "$$1 Target SIGNI on your field gets +5000 power until end of turn.\n" +
                "$$2 If it's your opponent's turn, target SIGNI on your field gains [[Shadow]] until end of turn."
        );
        
        setName("en_fan", "Illusions and Lightning Flashes");
        setDescription("en_fan",
                "=U There is a white and a green LRIG on your field\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your SIGNI, and until end of turn, it gets +5000 power.\n" +
                "$$2 If it is your opponent's turn, target 1 of your SIGNI, and until end of turn, it gains [[Shadow]]."
        );
        
		setName("zh_simplified", "幻影和稻光");
        setDescription("zh_simplified", 
                "=U你的场上有白色和绿色的分身\n" +
                "从以下的2种选1种。\n" +
                "$$1 你的精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n" +
                "$$2 对战对手的回合的场合，你的精灵1只作为对象，直到回合结束时为止，其得到[[暗影]]。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.WHITE, CardColor.GREEN);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(1);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() == 0 ||
               new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getSIGNICount(getOwner()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
                gainPower(target, 5000, ChronoDuration.turnEnd());
            } else if(!isOwnTurn())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityShadow(), ChronoDuration.turnEnd());
            }
        }
    }
}
