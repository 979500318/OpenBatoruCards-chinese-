package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class PIECE_K_DeathBeamDiva extends Card {
    
    public PIECE_K_DeathBeamDiva()
    {
        setImageSets("WXDi-P08-005", "PR-Di029");
        
        setOriginalName("デス・ビーム・ディーヴァ");
        setAltNames("デスビームディーヴァ Desu Biimu Diiva");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたの場に赤のルリグがいて対戦相手のセンタールリグがレベル３以上の場合、対戦相手は自分のエナゾーンからカード３枚を選びトラッシュに置く。\n" +
                "その後、あなたの場に青のルリグがいる場合、カードを３枚引く。\n" +
                "その後、あなたの場に緑のルリグがいる場合、あなたのシグニ１体を対象とし、ターン終了時まで、それは【ランサー】を得る。"
        );
        
        setName("en", "Death Beam Diva");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "If there is a red LRIG on your field and your opponent's Center LRIG is level three or more, your opponent chooses three cards from their Ener Zone and puts them into their trash. \n" +
                "Then, if there is a blue LRIG on your field, draw three cards.\n" +
                "Then, if there is a green LRIG on your field, target SIGNI on your field gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Death Beam Diva");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If there is a red LRIG on your field and your opponent's center LRIG is level 3 or higher, your opponent chooses 3 cards from their ener zone, and puts them into the trash.\n" +
                "Then, if there is a blue LRIG on your field, draw 3 cards.\n" +
                "Then, if there is a green LRIG on your field, target 1 of your SIGNI, and until end of turn, it gains [[Lancer]]."
        );
        
		setName("zh_simplified", "死亡·光束·照耀");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的场上有红色的分身且对战对手的核心分身在等级3以上的场合，对战对手从自己的能量区选3张牌放置到废弃区。（2张以下的场合，选这些全部）\n" +
                "然后，你的场上有蓝色的分身的场合，抽3张牌。\n" +
                "然后，你的场上有绿色的分身的场合，你的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
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
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.RED).getValidTargetsCount() > 0 &&
               getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(3, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0)
            {
                draw(3);
            }
            
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
    }
}
