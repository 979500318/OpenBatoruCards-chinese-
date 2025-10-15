package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_K4_MimicSuperTrap extends Card {

    public SIGNI_K4_MimicSuperTrap()
    {
        setImageSets("WXK01-102");

        setOriginalName("超罠　ミミック");
        setAltNames("チョウビンミミック Choubin Mimikku");
        setDescription("jp",
                "@E：カードを３枚引き、手札からカード３枚を好きな順番でデッキの一番上に置く。"
        );

        setName("en", "Mimic, Super Trap");
        setDescription("en",
                "@E: Draw 3 cards, and put 3 cards from your hand on the top of your deck in any order."
        );

		setName("zh_simplified", "超罠 宝箱怪");
        setDescription("zh_simplified", 
                "@E :抽3张牌，从手牌把3张牌任意顺序放置到牌组最上面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(4);
        setPower(10000);

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
            draw(3);
            
            DataTable<CardIndex> data = playerTargetCard(Math.min(3,getHandCount(getOwner())), new TargetFilter(TargetHint.TOP).own().fromHand());
            returnToDeck(data, DeckPosition.TOP);
        }
    }
}
