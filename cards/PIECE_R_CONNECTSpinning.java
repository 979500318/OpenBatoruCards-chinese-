package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_R_CONNECTSpinning extends Card {

    public PIECE_R_CONNECTSpinning()
    {
        setImageSets("WXDi-P14-002");

        setOriginalName("CONNECTスピニング");
        setAltNames("コネクトスピニング Konekuto Supiningu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の４つからあなたのセンタールリグのレベル１につき１つまで選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手のセンタールリグがレベル３以上の場合、対戦相手は自分のエナゾーンからカード３枚を選びトラッシュに置く。\n" +
                "$$3手札をすべて捨て、カードを４枚引く。\n" +
                "$$4手札を２枚捨ててもよい。そうした場合、対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Connect Spinning");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nChoose up to one of the following for each of your Center LRIG's levels.\n$$1Vanish target SIGNI on your opponent's field.\n$$2If your opponent's Center LRIG is level three or more, they choose and put three cards from their Ener Zone into their trash. \n$$3Discard your hand and draw four cards.\n$$4You may discard two cards. If you do, crush one of your opponent's Life Cloth."
        );
        
        setName("en_fan", "CONNECT Spinning");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose up to 1 of the following for each of your center LRIG's levels:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and banish it.\n" +
                "$$2 If your opponent's center LRIG is level 3 or higher, your opponent chooses 3 cards from their ener zone, and puts them into the trash.\n" +
                "$$3 Discard all cards from your hand, and draw 4 cards.\n" +
                "$$4 You may discard 2 cards from your hand. If you do, crush 1 of your opponent's life cloth."
        );

		setName("zh_simplified", "CONNECT回转");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的4种，依据你的核心分身的等级的数量，每有1级就选1种最多。\n" +
                "$$1 对战对手的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手的核心分身在等级3以上的场合，对战对手从自己的能量区选3张牌放置到废弃区。（2张以下的场合，选这些全部）\n" +
                "$$3 手牌全部舍弃，抽4张牌。\n" +
                "$$4 可以把手牌2张舍弃。这样做的场合，对战对手的生命护甲1张击溃。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(2));
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
            piece.setModeChoice(0,1);
            piece.setOnModesChosenPre(this::onPieceEffPreModesChoice);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreModesChoice()
        {
            piece.setModeChoice(0, Math.min(4, getLRIG(getOwner()).getIndexedInstance().getLevel().getValue()));
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & 1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            }
            if((modes & 1<<1) != 0)
            {
                if(getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
                {
                    DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(3, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                    trash(data);
                }
            }
            if((modes & 1<<2) != 0)
            {
                discard(getCardsInHand(getOwner()));
                draw(4);
            }
            if((modes & 1<<3) != 0)
            {
                if(discard(0,2, ChoiceLogic.BOOLEAN).size() == 2)
                {
                    crush(getOpponent());
                }
            }
        }
    }
}
