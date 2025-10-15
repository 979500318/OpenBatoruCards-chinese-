package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_B_SnowCircle extends Card {

    public ARTS_B_SnowCircle()
    {
        setImageSets("WX24-P1-022");

        setOriginalName("スノー・サークル");
        setAltNames("スノー・サークル Sunoo Saakuru");
        setDescription("jp",
                "カードを４枚引く。対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Snow Circle");
        setDescription("en",
                "Draw 4 cards. Choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "冰雪·圆环");
        setDescription("zh_simplified", 
                "抽4张牌。不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            draw(4);
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}

