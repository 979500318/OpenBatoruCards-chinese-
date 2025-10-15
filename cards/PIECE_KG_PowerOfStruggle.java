package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class PIECE_KG_PowerOfStruggle extends Card {

    public PIECE_KG_PowerOfStruggle()
    {
        setImageSets("WXDi-P15-002");

        setOriginalName("パワーオブストラグル");
        setAltNames("Pawaa obu Sutoraguru");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "このピースを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "以下の４つから２つまで選ぶ。追加でエクシード４を支払っていた場合、代わりに３つまで選ぶ。\n" +
                "$$1あなたのトラッシュから＜闘争派＞のシグニを２枚まで対象とし、それらを場に出す。\n" +
                "$$2あなたの＜闘争派＞のシグニ１体を選び、ターン終了時まで、それは【Ｓランサー】を得る。\n" +
                "$$3対戦相手のデッキの上からカードを１０枚トラッシュに置く。\n" +
                "$$4次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋10000する。"
        );

        setName("en", "Power of Struggle");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\nAs you use this PIECE, you may pay Exceed 4 as an additional use cost.\n\nChoose up to two of the following. If you paid the Exceed 4, choose up to three of the following instead.\n$$1Put up to two target <<War Division>> SIGNI from your trash onto your field.\n$$2Choose a <<War Division>> SIGNI on your field. It gains [[S Lancer]] until end of turn.\n$$3Put the top ten cards of your opponent's deck into their trash.\n$$4All SIGNI on your field get +10000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Power of Struggle");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "While using this piece, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "((If you additionally paid @[Exceed 4]@, choose up to 3 instead.))\n" +
                "$$1 Target up to 2 <<Struggle Faction>> SIGNI from your trash, and put them onto the field.\n" +
                "$$2 Choose 1 of your <<Struggle Faction>> SIGNI, and until end of turn, it gains [[S Lancer]].\n" +
                "$$3 Put the top 10 cards of your opponent's deck into the trash.\n" +
                "$$4 Until the end of your opponent's next turn, all of your SIGNI get +10000 power."
        );

		setName("zh_simplified", "斗争者力量");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "这张和音使用时，可以作为使用费用追加把@[超越 4]@支付。\n" +
                "从以下的4种选2种最多。追加把@[超越 4]@支付过的场合，作为替代，选3种最多。\n" +
                "$$1 从你的废弃区把<<闘争派>>精灵2张最多作为对象，将这些出场。\n" +
                "$$2 选你的<<闘争派>>精灵1只，直到回合结束时为止，其得到[[S枪兵]]。\n" +
                "$$3 从对战对手的牌组上面把10张牌放置到废弃区。\n" +
                "$$4 直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+10000。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK, CardColor.GREEN);
        setCost(Cost.colorless(1));
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
            piece.setAdditionalCost(new ExceedCost(4));
            piece.setModeChoice(0,2);
            piece.setOnModesChosenPre(this::onPieceEffPreModesChoice);
        }

        private void onPieceEffPreModesChoice()
        {
            if(piece.hasPaidAdditionalCost()) piece.setModeChoice(0,3);
        }
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            if((modes & 1<<0) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromTrash().playable());
                putOnField(data);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION)).get();
                if(target != null) attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
            }
            if((modes & 1<<2) != 0)
            {
                millDeck(getOpponent(), 10);
            }
            if((modes & 1<<3) != 0)
            {
                gainPower(getSIGNIOnField(getOwner()), 10000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
