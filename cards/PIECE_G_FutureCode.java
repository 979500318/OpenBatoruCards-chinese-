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
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class PIECE_G_FutureCode extends Card {
    
    public PIECE_G_FutureCode()
    {
        setImageSets("WXDi-P00-004");
        
        setOriginalName("フューチャー・コード");
        setAltNames("フューチャーコード Fyuuchaa Koodo");
        setDescription("jp",
                "=U あなたの場に緑のルリグがいる\n\n" +
                "あなたのパワー15000以上のシグニを２体まで対象とし、ターン終了時まで、それらは[[ランサー]]を得る。"
        );
        
        setName("en", "Future Code");
        setDescription("en",
                "=U You have a green LRIG on your field.\n\n" +
                "Up to two target SIGNI on your field with power 15000 or more gain [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Future Code");
        setDescription("en_fan",
                "=U There is a green LRIG on your field\n\n" +
                "Target up to 2 of your SIGNI with power 15000 or more, and until end of turn, they gain [[Lancer]]."
        );
        
		setName("zh_simplified", "未来·代号");
        setDescription("zh_simplified", 
                "=U你的场上有绿色的分身\n" +
                "你的力量15000以上的精灵2只最多作为对象，直到回合结束时为止，这些得到[[枪兵]]。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(4));
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
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() == 0) return ConditionState.BAD;
            
            return getSIGNICount(getOwner()) > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).own().SIGNI().withPower(15000,0)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                for(int i=0;i<piece.getTargets().size();i++)
                {
                    attachAbility(piece.getTarget(i), new StockAbilityLancer(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
