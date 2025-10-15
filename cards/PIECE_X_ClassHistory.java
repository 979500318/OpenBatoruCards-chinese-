package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class PIECE_X_ClassHistory extends Card {

    public PIECE_X_ClassHistory()
    {
        setImageSets("WX24-P1-035");

        setOriginalName("クラス・ヒストリー");
        setAltNames("クラスヒストリー Kurasu Hisutorii");
        setDescription("jp",
                "クラス１つを宣言する。あなたのデッキの上からカードを３枚見る。その中から宣言したクラスを持つシグニを、好きな枚数公開し手札に加えて好きな枚数エナゾーンに置く。残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Class History");
        setDescription("en",
                "Declare 1 class. Look at the top 3 cards of your deck. Reveal any number of SIGNI with the declared class from among them, add any number of them to your hand, and put any number of them into the ener zone. Put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "类别·历史");
        setDescription("zh_simplified", 
                "类别1种宣言。从你的牌组上面看3张牌。从中把持有宣言的类别的精灵，任意张数公开加入手牌，任意张数放置到能量区。剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerPieceAbility(this::onPieceEff);
        }

        private void onPieceEff()
        {
            CardSIGNIClass chosenClass = playerChoiceSIGNIClass();
            
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter().SIGNI().withClass(chosenClass).fromLooked());
            reveal(data);
            
            DataTable<CardIndex> dataToHand = playerTargetCard(0,data.size(), new TargetFilter().fromRevealed());
            addToHand(dataToHand);
            putInEner(getCardsInRevealed(getOwner()));
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

