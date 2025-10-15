package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_B_Seminar extends Card {

    public PIECE_B_Seminar()
    {
        setImageSets("WXDi-CP02-002");
        setLinkedImageSets("WXDi-CP02-005", "WXDi-CP02-006");

        setOriginalName("セミナー");
        setAltNames("Seminaa");
        setDescription("jp",
                "=U =E 青のルリグを１体以上含む\n" +
                "=U このゲームの間にあなたが《連邦生徒会》か《クロノス報道部》を使用している\n\n" +
                "対戦相手のルリグ１体を対象とし、それを凍結する。カードを４枚引く。\n" +
                "その後、あなたのルリグの下からカードを合計４枚ルリグトラッシュに置いてもよい。そうした場合、好きな生徒１人との絆を獲得する。"
        );

        setName("en", "Seminar");
        setDescription("en",
                "=U =E You have one or more blue LRIG on your team.\n=U You have used a \"General Student Council\" or a \"Kronos School of Journalism\" during this game.\n\nFreeze target LRIG on your opponent's field. Draw four cards.  \nThen, you may put four cards underneath LRIG on your field into their owner's LRIG Trash. If you do, start a relationship with any one student."
        );
        
        setName("en_fan", "Seminar");
        setDescription("en_fan",
                "=U =E with 1 or more being blue\n" +
                "=U You have used \"General Student Council\" or \"Kronos News Club\" this game.\n\n" +
                "Target 1 of your opponent's LRIG, and freeze it. Draw 4 cards.\n" +
                "Then, you may put a total of 4 cards from under your LRIG into the LRIG trash. If you do, gain a bond with a student of your choice."
        );

		setName("zh_simplified", "研讨会");
        setDescription("zh_simplified", 
                "=U=E含有蓝色的分身1只以上\n" +
                "=U这场游戏期间你把《連邦生徒会》或《クロノス報道部》使用过\n" +
                "对战对手的分身1只作为对象，将其冻结。抽4张牌。（冻结的分身在下一个的自己的竖直阶段不能竖直）\n" +
                "然后，可以从你的分身的下面把牌合计4张放置到分身废弃区。这样做的场合，获得与任意学生1人的羁绊。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLUE);
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

            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() > 0 &&
                    GameLog.getGameRecordsCount(e -> e.getId() == GameEventId.USE_PIECE && isOwnCard(e.getCaller()) &&
                     (e.getCaller().getCardReference().getOriginalName().equals("連邦生徒会") ||
                      e.getCaller().getCardReference().getOriginalName().equals("クロノス報道部"))) > 0  ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()));
        }
        private void onPieceEff()
        {
            freeze(piece.getTargets());
            
            draw(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,4, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().anyLRIG().withUnderType(CardUnderCategory.UNDER));
            if(trash(data) == 4)
            {
                playerChoiceBond();
            }
        }
    }
}
