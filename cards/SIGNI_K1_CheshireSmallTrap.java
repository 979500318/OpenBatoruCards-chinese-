package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_CheshireSmallTrap extends Card {

    public SIGNI_K1_CheshireSmallTrap()
    {
        setImageSets("WDK04-017");

        setOriginalName("小罠　チェシャ");
        setAltNames("ショウビンチェシャ Shoubin Chesha");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。それらを好きな順番でデッキの一番上に戻す。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Cheshire, Small Trap");
        setDescription("en",
                "@E: Look at the top 3 cards of your deck. Then, put them back on the top of your deck in any order." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "小罠 柴郡猫");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。将这些任意顺序返回牌组最上面。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(1000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
