package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
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
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_G_MaskedSwimsuitGang extends Card {

    public PIECE_G_MaskedSwimsuitGang()
    {
        setImageSets("WXDi-CP02-003");
        setLinkedImageSets("WXDi-CP02-005","WXDi-CP02-006");

        setOriginalName("覆面水着団");
        setAltNames("フクメンミズギダン Fukumen Mizuki Gan");
        setDescription("jp",
                "=U =E 緑のルリグを１体以上含む\n" +
                "=U このゲームの間にあなたが《連邦生徒会》か《クロノス報道部》を使用している\n\n" +
                "あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルし、あなたのデッキの上からカードを５枚見る。その中からカードを好きな枚数手札に加え、残りをエナゾーンに置く。\n" +
                "その後、あなたのルリグの下からカードを合計４枚ルリグトラッシュに置いてもよい。そうした場合、好きな生徒１人との絆を獲得する。"
        );

        setName("en", "Masked Swimsuit Gang");
        setDescription("en",
                "=U =E You have one or more green LRIG on your team.\n=U You have used a \"General Student Council\" or a \"Kronos School of Journalism\" during this game.\n\nShuffle all cards in your trash into your deck and look at the top five cards of your deck. Add any number of cards from among them to your hand and put the rest into your Ener Zone.\nThen, you may put four cards underneath LRIG on your field into their owner's LRIG Trash. If you do, start a relationship with any one student."
        );
        
        setName("en_fan", "Masked Swimsuit Gang");
        setDescription("en_fan",
                "=U =E with 1 or more being green\n" +
                "=U You have used \"General Student Council\" or \"Kronos News Club\" this game.\n\n" +
                "Shuffle all cards from your trash into your deck, and look at the top 5 cards of your deck. Add any number of cards from among them to your hand, and put the rest into the ener zone.\n" +
                "Then, you may put a total of 4 cards from under your LRIG into the LRIG trash. If you do, gain a bond with a student of your choice."
        );

		setName("zh_simplified", "蒙面泳装团");
        setDescription("zh_simplified", 
                "=U=E含有绿色的分身1只以上\n" +
                "=U这场游戏期间你把《連邦生徒会》或《クロノス報道部》使用过\n" +
                "你的废弃区的全部的牌加入牌组洗切，从你的牌组上面看5张牌。从中把牌任意张数加入手牌，剩下的放置到能量区。\n" +
                "然后，可以从你的分身的下面把牌合计4张放置到分身废弃区。这样做的场合，获得与任意学生1人的羁绊。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0 &&
                    GameLog.getGameRecordsCount(e -> e.getId() == GameEventId.USE_PIECE && isOwnCard(e.getCaller()) &&
                            (e.getCaller().getCardReference().getOriginalName().equals("連邦生徒会") ||
                                    e.getCaller().getCardReference().getOriginalName().equals("クロノス報道部"))) > 0  ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();

            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);

            putInEner(getCardsInLooked(getOwner()));
            
            data = playerTargetCard(0,4, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().anyLRIG().withUnderType(CardUnderCategory.UNDER));
            if(trash(data) == 4)
            {
                playerChoiceBond();
            }
        }
    }
}
