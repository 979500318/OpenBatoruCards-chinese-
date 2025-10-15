package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_CodeArtWAterServer extends Card {

    public SIGNI_B1_CodeArtWAterServer()
    {
        setImageSets("WX24-P4-069");

        setOriginalName("コードアート　Wオーターサーバー");
        setAltNames("コードアートダブリューオーターサーバー Koodo Aato Dabaryuu Ootaa Saabaa Water Server");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からスペルを１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。この方法でカードを手札に加えた場合、手札を１枚捨てる。" +
                "~#：カードを３枚引き、手札を１枚捨てる。"
        );

        setName("en", "Code Art W Ater Server");
        setDescription("en",
                "@E: Look at the top 5 cards of your deck. Reveal up to 1 spell from among them, add it to your hand, and put the rest on the bottom of your deck in any order. If you added a card to hand this way, discard 1 card from your hand." +
                "~#Draw 3 cards, and discard 1 card from your hand."
        );

		setName("zh_simplified", "必杀代号 饮水机");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把魔法1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。这个方法把牌加入手牌的场合，手牌1张舍弃。" +
                "~#抽3张牌，手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);

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
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
            reveal(cardIndex);
            boolean added = addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(added)
            {
                discard(1);
            }
        }

        private void onLifeBurstEff()
        {
            draw(3);
            discard(1);
        }
    }
}
