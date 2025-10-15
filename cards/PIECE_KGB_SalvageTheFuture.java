package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_KGB_SalvageTheFuture extends Card {
    
    public PIECE_KGB_SalvageTheFuture()
    {
        setImageSets("WXDi-D06-011", "PR-Di011");
        
        setOriginalName("salvage the future");
        setAltNames("サルベージザフューチャー Sarubeeji za Fuuyuuchaa");
        setDescription("jp",
                "=U =T ＜DIAGRAM＞＆全員レベル１以上\n\n" +
                "あなたのレベル３のルリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@U：このルリグがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のデッキの上からカードを１５枚トラッシュに置く。\n" +
                "$$2あなたのトラッシュから#Gを持たないシグニを３枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Salvage the Future");
        setDescription("en",
                "=U You have =T <<DIAGRAM>> on your field with all members level one or more.\n\n" +
                "Target level three LRIG on your field gains the following ability until end of turn." +
                "@>@U: Whenever this LRIG attacks, choose one of the following.\n" +
                "$$1 Put the top fifteen cards of your opponent's deck into the trash.\n" +
                "$$2 Add up to three target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Salvage the future");
        setDescription("en_fan",
                "=U =T <<DIAGRAM>> and all of them are level 1 or higher\n\n" +
                "Target 1 of your level 3 LRIGs, and until end of turn, it gains:" +
                "@>@U: When this LRIG attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Put the top 15 cards of your opponent's deck into the trash.\n" +
                "$$2 Target up to 3 SIGNI without #G @[Guard]@ from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "salvage the future");
        setDescription("zh_simplified", 
                "=U=T<<DIAGRAM>>＆全员等级1以上\n" +
                "你的等级3的分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@U :当这只分身攻击时，从以下的2种选1种。\n" +
                "$$1 从对战对手的牌组上面把15张牌放置到废弃区。\n" +
                "$$2 从你的废弃区把不持有#G的精灵3张最多作为对象，将这些加入手牌。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK, CardColor.BLUE, CardColor.GREEN);
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
            if(playerChoiceMode(piece) == 1)
            {
                millDeck(getOpponent(), 15);
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
                addToHand(data);
            }
        }
    }
}
