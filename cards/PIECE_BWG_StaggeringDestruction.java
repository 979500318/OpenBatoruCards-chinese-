package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_BWG_StaggeringDestruction extends Card {
    
    public PIECE_BWG_StaggeringDestruction()
    {
        setImageSets("WXDi-D05-011");
        
        setOriginalName("はんぱない★ディストラクション");
        setAltNames("ハンパナイディストラクション Hanpanai Disutorakushon");
        setDescription("jp",
                "=U =T ＜うちゅうのはじまり＞＆全員レベル１以上\n\n" +
                "あなたのレベル３のルリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@U：このルリグがアタックしたとき、以下の２つから１つを選ぶ。この効果を対戦相手のセンタールリグのレベルと同じ回数行う。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Hanpanai ★ Destruction");
        setDescription("en",
                "=U You have =T <<UCHU NO HAJIMARI>> on your field with all members level one or more.\n\n" +
                "Target level three LRIG on your field gains the following ability until end of turn." +
                "@>@U: Whenever this LRIG attacks, choose one of the following a number of times equal to the level of your opponent's center LRIG.\n" +
                "((Resolve three effects one at a time. You may choose the same effect multiple times.))\n" +
                "$$1 Draw a card.\n" +
                "$$2 Your opponent discards a card at random."
        );
        
        setName("en_fan", "Staggering★Destruction");
        setDescription("en_fan",
                "=U =T <<Universe's Beginning>> and all of them are level 1 or higher\n\n" +
                "Target 1 of your level 3 LRIGs, and until end of turn, it gains:" +
                "@>@U: Whenever this LRIG attacks, @[@|choose 1 of the following:|@]@\n" +
                "((Perform this effect the same number of times as your opponent's center LRIG's level))\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Choose 1 card from your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "震撼★击毁");
        setDescription("zh_simplified", 
                "=U=T<<うちゅうのはじまり>>＆全员等级1以上\n" +
                "你的等级3的分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@U :当这只分身攻击时，从以下的2种选1种。这个效果进行与对战对手的核心分身的等级相同的次数。（进行各1次。可以选择相同的选项）\n" +
                "$$1 抽1张牌。\n" +
                "$$2 不看对战对手的手牌选1张，舍弃。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE, CardColor.WHITE, CardColor.GREEN);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            if(new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() != 3) return ConditionState.BAD;
            
            return new TargetFilter().own().LRIG().withLevel(3).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(3)));
        }
        private void onPieceEff()
        {
            if(piece.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(piece.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            int levelLRIGOP = getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue();
            
            for(int i=0;i<levelLRIGOP;i++)
            {
                if(playerChoiceMode(piece) == 1)
                {
                    draw(1);
                } else {
                    CardIndex cardIndexDiscard = playerChoiceHand().get();
                    discard(cardIndexDiscard);
                }
            }
        }
    }
}
