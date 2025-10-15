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
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_B_SpaceClassMotherhood extends Card {

    public PIECE_B_SpaceClassMotherhood()
    {
        setImageSets("WXDi-P09-002");

        setOriginalName("宇宙級母性");
        setAltNames("スペースマザー Supeesu Mazaa");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "対戦相手のすべてのシグニをデッキの一番上に置く。"
        );

        setName("en", "Space Mother");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Put all SIGNI on your opponent's field on top of their owner's deck. "
        );
        
        setName("en_fan", "Space-Class Motherhood");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Return all of your opponent's SIGNI to the top of their deck."
        );

		setName("zh_simplified", "宇宙级母性");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "对战对手的全部的精灵放置到牌组最上面。（放置的顺序由对战对手决定）\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(6));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            DataTable<CardIndex> data = playerTargetCard(getOpponent(), getSIGNICount(getOpponent()),getSIGNICount(getOpponent()), ChoiceLogic.DEFAULT, new TargetFilter(TargetHint.TOP).own().SIGNI(), null, false);
            returnToDeck(data, DeckPosition.TOP);
        }
    }
}

