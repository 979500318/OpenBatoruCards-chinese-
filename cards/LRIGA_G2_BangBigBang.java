package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Game.GamePlayerRole;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.game.FieldZone;

import java.util.function.Predicate;

public final class LRIGA_G2_BangBigBang extends Card {

    public LRIGA_G2_BangBigBang()
    {
        setImageSets("WXDi-P01-026");

        setOriginalName("バン＝ビッグバン");
        setAltNames("バンビッグバン Ban Bigguban");
        setDescription("jp",
                "@E：各プレイヤーは自分のシグニゾーンとトラッシュにあるすべてのカードをデッキに加えてシャッフルする。その後、各プレイヤーは自分のデッキの上からカードを７枚見て、その中から好きな枚数のシグニを場に出し、残りをトラッシュに置く。それらのシグニの@E能力は発動しない。"
        );

        setName("en", "Bang =Big Bang=");
        setDescription("en",
                "@E: Each player shuffles all cards in their SIGNI Zone and trash back into their deck, then looks at the top seven cards of their deck. Each player may put any number of SIGNI from among their seven cards onto their field, and puts the rest into the trash. The @E abilities of SIGNI put onto the field this way do not activate."
        );

        setName("en_fan", "Bang-Big Bang");
        setDescription("en_fan",
                "@E: Each player shuffles all their cards from their SIGNI zones and trash into their deck. After that, each player looks at the top 7 cards of their deck, puts any number of SIGNI from among them onto the field, and puts the rest into the trash. The @E abilities of those SIGNI don't activate."
        );

		setName("zh_simplified", "梆=大梆炸");
        setDescription("zh_simplified", 
                "@E 各玩家把自己的精灵区和废弃区的全部的牌加入牌组洗切。然后，各玩家从自己的牌组上面看7张牌，从中把任意张数的精灵出场，剩下的放置到废弃区。这些精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(7));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private static final Predicate<CardIndex> FILTER_EXCLUDE_CRAFTS = cardIndex -> cardIndex.getCardReference().isCraft();
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            returnToDeck(getSIGNIOnField(getOwner()).andRemoveIf(FILTER_EXCLUDE_CRAFTS), DeckPosition.TOP);
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck(getOwner());
            
            returnToDeck(getSIGNIOnField(getOpponent()).andRemoveIf(FILTER_EXCLUDE_CRAFTS), DeckPosition.TOP);
            returnToDeck(getCardsInTrash(getOpponent()), DeckPosition.TOP);
            shuffleDeck(getOpponent());
            
            look(7, getOwner());
            look(7, getOpponent());
            
            int numPlayableZonesTurn = new TargetFilter().ownedBy(getTurnPlayer()).SIGNI().zone().playable().getValidTargetsCount();
            int numPlayableZonesOP = new TargetFilter().ownedBy(GamePlayerRole.getOpponentRole(getTurnPlayer())).SIGNI().zone().playable().getValidTargetsCount();
            
            DataTable<CardIndex> dataTurn = playerTargetCard(getTurnPlayer(), 0,numPlayableZonesTurn, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            DataTable<CardIndex> dataOP = playerTargetCard(GamePlayerRole.getOpponentRole(getTurnPlayer()), 0,numPlayableZonesOP, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            
            DataTable<FieldZone> dataZonesTurn = playerTargetZone(getTurnPlayer(), dataTurn.size(), new TargetFilter(TargetHint.FIELD).own().SIGNI().playable());
            DataTable<FieldZone> dataZonesOP = playerTargetZone(GamePlayerRole.getOpponentRole(getTurnPlayer()), dataOP.size(), new TargetFilter(TargetHint.FIELD).own().SIGNI().playable());
            
            putOnField(dataTurn, dataZonesTurn, Enter.DONT_ACTIVATE);
            putOnField(dataOP, dataZonesOP, Enter.DONT_ACTIVATE);
            
            trash(getCardsInLooked(getOwner()));
            trash(getCardsInLooked(getOpponent()));
        }
    }
}

