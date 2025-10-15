package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W1_SombreroNaturalStar extends Card {

    public SIGNI_W1_SombreroNaturalStar()
    {
        setImageSets("WX24-P4-057");

        setOriginalName("羅星　ソンブレロ");
        setAltNames("ラセイソンブレロ Rasei Sonburero");
        setDescription("jp",
                "@E %X：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番上に戻す。"
        );

        setName("en", "Sombrero, Natural Star");
        setDescription("en",
                "@E %X: Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, add it to your hand, and put the rest on the top of your deck in any order."
        );

		setName("zh_simplified", "罗星 草帽星系");
        setDescription("zh_simplified", 
                "@E %X:从你的牌组上面看3张牌。从中把精灵1张公开加入手牌，剩下的任意顺序返回牌组最上面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
