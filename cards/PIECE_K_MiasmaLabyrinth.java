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

public final class PIECE_K_MiasmaLabyrinth extends Card {
    
    public PIECE_K_MiasmaLabyrinth()
    {
        setImageSets("WXDi-P06-003");
        
        setOriginalName("マイアズマ・ラビリンス");
        setAltNames("マイアズマラビリンス Maiazuma Rabirinsu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の４つからあなたのセンタールリグのレベル１につき１つまで選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。\n" +
                "$$2あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。\n" +
                "$$3あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$4各プレイヤーは自分のデッキの上からカードを７枚トラッシュに置く。"
        );
        
        setName("en", "Miasma Labyrinth");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Choose one of the following for each of your Center LRIG's levels.\n" +
                "$$1 Target SIGNI on your opponent's field gets --12000 power until end of turn.\n" +
                "$$2 Put target SIGNI from your trash onto your field.\n" +
                "$$3 Add target SIGNI from your trash to your hand.\n" +
                "$$4 Each player puts the top seven cards of their deck into their trash."
        );
        
        setName("en_fan", "Miasma Labyrinth");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "For each level of your center LRIG, @[@|choose 1 of the folllowing:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power.\n" +
                "$$2 Target 1 SIGNI from your trash, and put it onto the field.\n" +
                "$$3 Target 1 SIGNI from your trash, and add it to your hand.\n" +
                "$$4 Each player puts the top 7 cards of their deck into the trash."
        );
        
		setName("zh_simplified", "瘴气·迷牢");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的4种依据你的核心分身的等级的数量，每有1级就选1种最多。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。\n" +
                "$$2 从你的废弃区把精灵1张作为对象，将其出场。\n" +
                "$$3 从你的废弃区把精灵1张作为对象，将其加入手牌。\n" +
                "$$4 各玩家从自己的牌组上面把7张牌放置到废弃区。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2));
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
            piece.setModeChoice(1);
            piece.setOnModesChosenPre(this::onPieceEffPreModesChoice);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreModesChoice()
        {
            piece.setModeChoice(Math.min(4, getLRIG(getOwner()).getIndexedInstance().getLevel().getValue()));
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & (1<<0)) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
            if((modes & (1<<1)) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
                putOnField(target);
            }
            if((modes & (1<<2)) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
                addToHand(target);
            }
            if((modes & (1<<3)) != 0)
            {
                millDeck(7);
                millDeck(getOpponent(), 7);
            }
        }
    }
}
