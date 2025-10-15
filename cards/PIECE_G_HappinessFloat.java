package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class PIECE_G_HappinessFloat extends Card {

    public PIECE_G_HappinessFloat()
    {
        setImageSets("WXDi-P14-004");

        setOriginalName("ハピネス・フロート");
        setAltNames("ハピネスフロート Hapinesu Furooto");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンからシグニを２枚まで対象とし、それらを場に出す。ターン終了時まで、あなたのすべてのシグニは【Ｓランサー】を得る。\n" +
                "$$2あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルする。あなたのセンタールリグのレベル１につき【エナチャージ２】をする。"
        );

        setName("en", "Happiness Float");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nChoose one of the following.\n$$1Put up to two target SIGNI from your Ener Zone onto your field. All SIGNI on your field gain [[S Lancer]] until end of turn.\n$$2Shuffle all cards in your trash into your deck. [[Ener Charge 2]] for each of your Center LRIG's levels."
        );
        
        setName("en_fan", "Happiness Float");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 SIGNI from your ener zone, and put them onto the field. Until end of turn, all of your SIGNI gain [[S Lancer]].\n" +
                "$$2 Shuffle all cards from your trash into your deck. For each of your center LRIG's levels, [[Ener Charge 2]]."
        );

		setName("zh_simplified", "幸福·泳圈");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的2种选1种。\n" +
                "$$1 从你的能量区把精灵2张最多作为对象，将这些出场。直到回合结束时为止，你的全部的精灵得到[[S枪兵]]。\n" +
                "$$2 你的废弃区的全部的牌加入牌组洗切。依据你的核心分身的等级的数量，每有1级就[[能量填充2]]。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
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
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable());
                putOnField(data);
                
                forEachSIGNIOnField(getOwner(), cardIndex -> attachAbility(cardIndex, new StockAbilitySLancer(), ChronoDuration.turnEnd()));
            } else {
                returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
                shuffleDeck();
                
                enerCharge(2 * getLRIG(getOwner()).getIndexedInstance().getLevel().getValue());
            }
        }
    }
}
