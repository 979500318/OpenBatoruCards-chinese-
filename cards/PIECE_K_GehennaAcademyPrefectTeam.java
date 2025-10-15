package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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

public final class PIECE_K_GehennaAcademyPrefectTeam extends Card {

    public PIECE_K_GehennaAcademyPrefectTeam()
    {
        setImageSets("WXDi-CP02-004");
        setLinkedImageSets("WXDi-CP02-005", "WXDi-CP02-006");

        setOriginalName("ゲヘナ学園風紀委員会");
        setAltNames("ゲヘナガクエンフウキイインカイ Gehena Gakuen Fuuki Iinkai");
        setDescription("jp",
                "=U =E 黒のルリグを１体以上含む\n" +
                "=U このゲームの間にあなたが《連邦生徒会》か《クロノス報道部》を使用している\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。あなたのトラッシュから#Gを持たないシグニを３枚まで対象とし、それらを手札に加える。対戦相手のデッキの上からカードを８枚トラッシュに置く。\n" +
                "その後、あなたのルリグの下からカードを合計４枚ルリグトラッシュに置いてもよい。そうした場合、好きな生徒１人との絆を獲得する。"
        );

        setName("en", "Gehenna Prefect Team");
        setDescription("en",
                "=U =E You have one or more black LRIG on your team.\n=U You have used a \"General Student Council\" or a \"Kronos School of Journalism\" during this game.\n\nTarget SIGNI on your opponent's field gets --8000 power until end of turn. Add up to three target SIGNI without a #G from your trash to your hand. Put the top eight cards of your opponent's deck into their trash.\nThen, you may put four cards underneath LRIG on your field into their owner's LRIG Trash. If you do, start a relationship with any one student."
        );
        
        setName("en_fan", "Gehenna Academy Prefect Team");
        setDescription("en_fan",
                "=U =E with 1 or more being black\n" +
                "=U You have used \"General Student Council\" or \"Kronos News Club\" this game.\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power. Target up to 3 SIGNI without #G @[Guard]@ from your trash, and add them to your hand. Put the top 8 cards of your opponent's deck into the trash.\n" +
                "Then, you may put a total of 4 cards from under your LRIG into the LRIG trash. If you do, gain a bond with a student of your choice."
        );

		setName("zh_simplified", "歌赫娜学园风纪委员会");
        setDescription("zh_simplified", 
                "=U=E含有黑色的分身1只以上\n" +
                "=U这场游戏期间你把《連邦生徒会》或《クロノス報道部》使用过\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。从你的废弃区把不持有#G的精灵3张最多作为对象，将这些加入手牌。从对战对手的牌组上面把8张牌放置到废弃区。\n" +
                "然后，可以从你的分身的下面把牌合计4张放置到分身废弃区。这样做的场合，获得与任意学生1人的羁绊。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLACK).getValidTargetsCount() > 0 &&
                    GameLog.getGameRecordsCount(e -> e.getId() == GameEventId.USE_PIECE && isOwnCard(e.getCaller()) &&
                     (e.getCaller().getCardReference().getOriginalName().equals("連邦生徒会") ||
                      e.getCaller().getCardReference().getOriginalName().equals("クロノス報道部"))) > 0  ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onPieceEff()
        {
            gainPower(piece.getTarget(), -8000, ChronoDuration.turnEnd());

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            addToHand(data);
            
            millDeck(getOpponent(), 8);
            
            data = playerTargetCard(0,4, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().anyLRIG().withUnderType(CardUnderCategory.UNDER));
            if(trash(data) == 4)
            {
                playerChoiceBond();
            }
        }
    }
}
