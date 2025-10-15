package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.animations.AnimationSpinnerRotateSingle;
import open.batoru.game._3d.Group3D;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneSpinner;
import open.batoru.game.gfx.GFXZoneSpinner.GFXFlatSpinnerObject;

public final class PIECE_K_ChaosChaosChaos extends Card {

    public PIECE_K_ChaosChaosChaos()
    {
        setImageSets("WXDi-P14-005");

        setOriginalName("カオス！chaos！混沌！");
        setAltNames("カオスカオスカオス Kaosu Kaosu Kaosu");
        setDescription("jp",
                "=U =E 以下の３つから２つまで選ぶ。\n\n" +
                "以下の３つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニを３枚まで対象とし、それらを手札に加える。\n" +
                "$$2対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらのパワーをそれぞれ－10000する。\n" +
                "$$3対戦相手はデッキの上からカードを１０枚トラッシュに置く。次の対戦相手のメインフェイズとアタックフェイズの間、対戦相手のトラッシュにあるカードは対戦相手の効果によって他の領域に移動しない。"
        );

        setName("en", "Chaos! Chaos! Chaos!");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nChoose up to two of the following.\n$$1Add up to three target SIGNI that share a color with your Center LRIG from your trash to your hand.\n$$2Up to two target SIGNI on your opponent's field get --10000 power until end of turn.\n$$3Your opponent puts the top ten cards of their deck into their trash. During your opponent's next main phase and attack phase, cards in your opponent's trash cannot be moved to other Zones by your opponent's effects."
        );
        
        setName("en_fan", "Chaos! Chaos! Chaos!");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Target up to 3 SIGNI that share a common color with your center LRIG from your trash, and add them to your hand.\n" +
                "$$2 Target up to 2 of your opponent's SIGNI, and until end of turn, they get --10000 power.\n" +
                "$$3 Put the top 10 cards of your opponent's deck into the trash. During your opponent's next main phase and attack phase, cards in your opponent's trash can't be moved to other zones by your opponent's effects."
        );

		setName("zh_simplified", "卡欧斯！chaos！混沌！");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的3种选2种最多。\n" +
                "$$1 从你的废弃区把持有与你的核心分身共通颜色的精灵3张最多作为对象，将这些加入手牌。\n" +
                "$$2 对战对手的精灵2只最多作为对象，直到回合结束时为止，这些的力量各-10000。\n" +
                "$$3 从对战对手的牌组上面把10张牌放置到废弃区。下一个对战对手的主要阶段和攻击阶段期间，对战对手的废弃区的牌不会因为对战对手的效果往其他的领域移动。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1));
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
            piece.setModeChoice(0,2);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & 1) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()));
                addToHand(data);
            }
            if((modes & 1<<1) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.MINUS).OP().SIGNI());
                gainPower(data, -10000, ChronoDuration.turnEnd());
            }
            if((modes & 1<<2) != 0)
            {
                millDeck(getOpponent(), 10);
                
                int currentTurn = getTurnCount();
                ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
                ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().OP().fromTrash(), new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_MOVED, data -> {
                    if(getTurnCount() != currentTurn &&
                       (getCurrentPhase() == GamePhase.MAIN || GamePhase.isAttackPhase(getCurrentPhase())) &&
                        data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex())) return RuleCheckState.BLOCK;
                    
                    return RuleCheckState.IGNORE;
                }));
                GFX.attachToChronoRecord(record, new GFXZoneSpinner(getOpponent(),CardLocation.TRASH, new AnimationSpinnerRotateSingle(8000, -10), new Group3D[]{new GFXFlatSpinnerObject("zones/chain_circle")}));
                attachPlayerAbility(getOwner(), attachedConstShared, record);
            }
        }
    }
}

