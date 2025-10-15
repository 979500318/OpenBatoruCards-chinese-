package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;

public final class SIGNI_K3_BarabaraGreatTrap extends Card {

    public SIGNI_K3_BarabaraGreatTrap()
    {
        setImageSets("WDK04-013");

        setOriginalName("大罠　バラバラ");
        setAltNames("ダイビンバラバラ Daibin Barabara");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から好きな枚数のカードをトラッシュに置き、残りを好きな順番でデッキの一番上に戻す。"
        );

        setName("en", "Barabara, Great Trap");
        setDescription("en",
                "@E: Look at the top 3 cards of your deck. Put any number of cards from among them into the trash, and put the rest on the top of your deck in any order."
        );

		setName("zh_simplified", "大罠 人体错位");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把任意张数的牌放置到废弃区，剩下的任意顺序返回牌组最上面。（牌组在2张以下的场合看这些全部）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.TRASH).own().fromLooked());
            trash(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
