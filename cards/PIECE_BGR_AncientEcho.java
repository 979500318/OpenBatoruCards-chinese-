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

public final class PIECE_BGR_AncientEcho extends Card {
    
    public PIECE_BGR_AncientEcho()
    {
        setImageSets("WXDi-P00-003");
        
        setOriginalName("アンシエント・エコー");
        setAltNames("アンシエントエコー Anshiento Ekoo");
        setDescription("jp",
                "=U あなたの場に赤と青と緑のルリグがいる\n\n" +
                "対戦相手のパワー１００００以下のシグニ１体を対象とし、それをバニッシュする。カードを２枚引く。ターン終了時まで、あなたのすべてのシグニのパワーを＋３０００する。"
        );
        
        setName("en", "Ancient Echo");
        setDescription("en",
                "=U You have a red LRIG, a blue LRIG, and a green LRIG on your field.\n\n" +
                "Vanish target SIGNI on your opponent's field with power 10000 or less. Draw two cards. All SIGNI on your field get +3000 power until end of turn."
        );
        
        setName("en_fan", "Ancient Echo");
        setDescription("en_fan",
                "=U There is a red, a blue, and a green LRIG on your field\n\n" +
                "Target 1 of your opponent's SIGNI with power 10000 or less, and banish it. Draw 2 cards. Until end of turn, all of your SIGNI get +3000 power."
        );
        
		setName("zh_simplified", "远古·回声");
        setDescription("zh_simplified", 
                "=U你的场上有红色和蓝色和绿色的分身\n" +
                "对战对手的力量10000以下的精灵1只作为对象，将其破坏。抽2张牌。直到回合结束时为止，你的全部的精灵的力量+3000。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE, CardColor.RED, CardColor.GREEN);
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
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return (new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0 &&
                    new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0 &&
                    new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)));
        }
        private void onPieceEff()
        {
            banish(piece.getTarget());
            
            draw(2);
            
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.turnEnd());
        }
    }
}
