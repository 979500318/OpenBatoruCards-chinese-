package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_PyxisNaturalStar extends Card {

    public SIGNI_B2_PyxisNaturalStar()
    {
        setImageSets("WX24-P3-078");

        setOriginalName("羅星　ピクシス");
        setAltNames("ラセイピクシス Rasei Pikushisu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中からカード１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。" +
                "~#：カードを３枚引き、手札を１枚捨てる。"
        );

        setName("en", "Pyxis, Natural Star");
        setDescription("en",
                "@E: Look at the top 3 cards of your deck. Put 1 card from among them on the top of your deck, and put the rest on the bottom of your deck in any order." +
                "~#Draw 3 cards, and discard 1 card from your hand."
        );

		setName("zh_simplified", "罗星 罗盘座");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把1张牌返回牌组最上面，剩下的任意顺序放置到牌组最下面。" +
                "~#抽3张牌，手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            draw(3);
            discard(1);
        }
    }
}
