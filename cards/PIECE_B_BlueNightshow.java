package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_B_BlueNightshow extends Card {

    public PIECE_B_BlueNightshow()
    {
        setImageSets("WXDi-P14-003");

        setOriginalName("ブルー・ナイトショー");
        setAltNames("ブルーナイトショー Buruu Naito Shoo");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の４つからあなたのセンタールリグのレベル１につき１つまで選ぶ。\n" +
                "$$1対戦相手のレベル２以下のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "$$2対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "$$3カードを３枚引く。\n" +
                "$$4対戦相手の手札を２枚見ないで選び、捨てさせる。"
        );

        setName("en", "Blue Night Show");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nChoose up to one of the following for each of your Center LRIG's levels.\n$$1Put target level two or less SIGNI on your opponent's field on the bottom of its owner's deck.\n$$2Freeze target LRIG on your opponent's field.\n$$3Draw three cards.\n$$4Your opponent discards two cards at random."
        );
        
        setName("en_fan", "Blue Nightshow");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose 1 of the following for each of your center LRIG's levels:|@]@\n" +
                "$$1 Target 1 of your opponent's level 2 or lower SIGNI, and put it on the bottom of their deck.\n" +
                "$$2 Target 1 of your opponent's LRIG, and freeze it.\n" +
                "$$3 Draw 3 cards.\n" +
                "$$4 Choose 2 cards from your opponent's hand without looking, and your opponent discards them."
        );

		setName("zh_simplified", "蔚蓝·夜场");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的4种，依据你的核心分身的等级的数量，每有1级就选1种最多。\n" +
                "$$1 对战对手的等级2以下的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "$$2 对战对手的分身1只作为对象，将其冻结。\n" +
                "$$3 抽3张牌。\n" +
                "$$4 不看对战对手的手牌选2张，舍弃。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(2));
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

            if((modes & 1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
                freeze(target);
            }
            if((modes & 1<<2) != 0)
            {
                draw(3);
            }
            if((modes & 1<<3) != 0)
            {
                DataTable<CardIndex> data = playerChoiceHand(2);
                discard(data);
            }
        }
    }
}
