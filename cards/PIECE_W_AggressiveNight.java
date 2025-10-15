package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_W_AggressiveNight extends Card {
    
    public PIECE_W_AggressiveNight()
    {
        setImageSets("WXDi-P08-001", "PR-Di025");
        
        setOriginalName("アグレッシブな夜");
        setAltNames("アグレッシブなヨル Aguresshibu Na Yori");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたの場に青のルリグがいる場合、対戦相手の手札を見て１枚選び、捨てさせる。\n" +
                "その後、あなたの場に緑のルリグがいる場合、【エナチャージ３】をする。\n" +
                "その後、あなたの場に黒のルリグがいる場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );
        
        setName("en", "Aggressive Night");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "If there is a blue LRIG on your field, look at your opponent's hand and choose a card. Your opponent discards it.\n" +
                "Then, if there is a green LRIG on your field, [[Ener Charge 3]].\n" +
                "Then, if there is a black LRIG on your field, target SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Aggressive Night");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If there is a blue LRIG on your field, look at your opponent's hand, choose 1 card from it, and discard it.\n" +
                "Then, if there is a green LRIG on your field, [[Ener Charge 3]].\n" +
                "Then, if there is a black LRIG on your field, target 1 of your opponent's SIGNI, and until end of turn, it gets --15000 power."
        );
        
		setName("zh_simplified", "侵略之夜");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的场上有蓝色的分身的场合，看对战对手的手牌选1张，舍弃。\n" +
                "然后，你的场上有绿色的分身的场合，[[能量填充3]]。\n" +
                "然后，你的场上有黑色的分身的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE);
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
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0)
            {
                reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
                
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
                discard(cardIndex);
                
                addToHand(getCardsInRevealed(getOpponent()));
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0)
            {
                enerCharge(3);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            }
        }
    }
}
