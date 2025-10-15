package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_K_Instigate extends Card {

    public PIECE_K_Instigate()
    {
        setImageSets("WXDi-P13-002");

        setOriginalName("Instigate");
        setAltNames("インスティゲイト Insutigeito");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたか対戦相手のデッキの上からカードを８枚トラッシュに置く。その後、あなたのトラッシュから#Sのシグニを２枚まで対象とし、それらを場に出す。対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にある#Sのシグニ１体につき－5000する。"
        );

        setName("en", "Instigate");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nPut the top eight cards of your deck or your opponent's deck into the trash. Then, put up to two target #S SIGNI from your trash onto your field. Target SIGNI on your opponent's field gets --5000 power for each #S SIGNI on your field until end of turn."
        );
        
        setName("en_fan", "Instigate");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Put the top 8 cards of your or your opponent's deck into the trash. Then, put up to 2 #S @[Dissona]@ SIGNI from your trash onto the field. Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power for each of your #S @[Dissona]@ SIGNI."
        );

		setName("zh_simplified", "Instigate");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从你或对战对手的牌组上面把8张牌放置到废弃区。然后，从你的废弃区把#S的精灵2张最多作为对象，将这些出场。对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的#S的精灵的数量，每有1只就-5000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 8);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().dissona().fromTrash().playable());
            putOnField(data);

            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000 * new TargetFilter().own().SIGNI().dissona().getValidTargetsCount(), ChronoDuration.turnEnd());
        }
    }
}
