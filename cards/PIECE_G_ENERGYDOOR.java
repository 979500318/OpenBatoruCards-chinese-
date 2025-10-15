package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class PIECE_G_ENERGYDOOR extends Card {

    public PIECE_G_ENERGYDOOR()
    {
        setImageSets("WXDi-P07-002");

        setOriginalName("ENERGY DOOR");
        setAltNames("エナジードアー Enajii Doaa");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の４つからあなたのセンタールリグのレベル１につき１つまで選ぶ。\n" +
                "$$1あなたのトラッシュから#Gを持たないカードを２枚まで対象とし、それらをエナゾーンに置く。\n" +
                "$$2カードを２枚引く。\n" +
                "$$3対戦相手のレベル３以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$4次の対戦相手のターンの間、あなたが対戦相手のルリグによって最初にダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "ENERGY DOOR");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Choose one of the following for each of your Center LRIG's levels.\n" +
                "$$1 Put up to two target cards without a #G from your trash into your Ener Zone.\n" +
                "$$2 Draw two cards.\n" +
                "$$3 Vanish target level three or more SIGNI on your opponent's field.\n" +
                "$$4 During your opponent's next turn, if you would be the first to take damage from your opponent's LRIG, instead you do not take that damage."
        );
        
        setName("en_fan", "ENERGY DOOR");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "For each level of your center LRIG, @[@|choose 1 of the folllowing:|@]@\n" +
                "$$1 Target up to 2 cards without #G @[Guard]@ from your trash, and put them into the ener zone.\n" +
                "$$2 Draw 2 cards.\n" +
                "$$3 Target 1 of your opponent's level 3 or higher SIGNI, and banish it.\n" +
                "$$4 During your opponent's next turn, if its the first time you would be damaged by your opponent's LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "ENERGY DOOR");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的4种依据你的核心分身的等级的数量，每有1级选1种最多。\n" +
                "$$1 从你的废弃区把不持有#G的牌2张最多作为对象，将这些放置到能量区。\n" +
                "$$2 抽2张牌。\n" +
                "$$3 对战对手的等级3以上的精灵1只作为对象，将其破坏。\n" +
                "$$4 下一个对战对手的回合期间，你因为对战对手的分身受到最初的伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2));
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
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
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
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
                putInEner(data);
            }
            if((modes & (1<<1)) != 0)
            {
                draw(2);
            }
            if((modes & (1<<2)) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3,0)).get();
                banish(target);
            }
            if((modes & (1<<3)) != 0)
            {
                ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
                GFX.attachToChronoRecord(record, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "generic", new int[]{50,205,50}));
                
                blockNextDamage(record, cardIndexSnapshot -> !isOwnTurn() && !isOwnCard(cardIndexSnapshot) && CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
            }
        }
    }
}

