package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
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
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;

public final class PIECE_W_TeaParty extends Card {

    public PIECE_W_TeaParty()
    {
        setImageSets("WXDi-CP02-001");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET, "WXDi-CP02-005","WXDi-CP02-006");

        setOriginalName("ティーパーティー");
        setAltNames("Tii Paatii");
        setDescription("jp",
                "=U =E 白のルリグを１体以上含む\n" +
                "=U このゲームの間にあなたが《連邦生徒会》か《クロノス報道部》を使用している\n\n" +
                "あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。【シグニバリア】１つを得る。\n" +
                "その後、あなたのルリグの下からカードを合計４枚ルリグトラッシュに置いてもよい。そうした場合、好きな生徒１人との絆を獲得する。"
        );

        setName("en", "Tea Party");
        setDescription("en",
                "=U =E You have one or more white LRIG on your team.\n=U You have used a \"General Student Council\" or a \"Kronos School of Journalism\" during this game.\n\nLook at the top five cards of your deck. Add up to one card from among them to your hand and put the rest on the bottom of your deck in any order. Gain a [[SIGNI Barrier]]. \nThen, you may put four cards underneath LRIG on your field into their owner's LRIG Trash. If you do, start a relationship with any one student."
        );
        
        setName("en_fan", "Tea Party");
        setDescription("en_fan",
                "=U =E with 1 or more being white\n" +
                "=U You have used \"General Student Council\" or \"Kronos News Club\" this game.\n\n" +
                "Look at the top 5 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order. Gain 1 [[SIGNI Barrier]].\n" +
                "Then, you may put a total of 4 cards from under your LRIG into the LRIG trash. If you do, gain a bond with a student of your choice."
        );

		setName("zh_simplified", "茶话会");
        setDescription("zh_simplified", 
                "=U=E含有白色的分身1只以上\n" +
                "=U这场游戏期间你把《連邦生徒会》或《クロノス報道部》使用过\n" +
                "从你的牌组上面看5张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。得到[[精灵屏障]]1个。（你下一次从精灵受到伤害的场合，作为替代，消费[[精灵屏障]]1个，不会受到那次伤害）\n" +
                "然后，可以从你的分身的下面把牌合计4张放置到分身废弃区。这样做的场合，获得与任意学生1人的羁绊。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE);
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() > 0 &&
                    GameLog.getGameRecordsCount(e -> e.getId() == GameEventId.USE_PIECE && isOwnCard(e.getCaller()) &&
                     (e.getCaller().getCardReference().getOriginalName().equals("連邦生徒会") ||
                      e.getCaller().getCardReference().getOriginalName().equals("クロノス報道部"))) > 0  ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilitySIGNIBarrier(), ChronoDuration.permanent());
            
            DataTable<CardIndex> data = playerTargetCard(0,4, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().anyLRIG().withUnderType(CardUnderCategory.UNDER));
            if(trash(data) == 4)
            {
                playerChoiceBond();
            }
        }
    }
}
