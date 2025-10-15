package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;

public final class ARTS_B_GlareAt extends Card {

    public ARTS_B_GlareAt()
    {
        setImageSets("WX24-P4-030");

        setOriginalName("グレア・アット");
        setAltNames("グレアアット Gurea Atto");
        setDescription("jp",
                "カードを４枚引き、手札を２枚捨てる。この方法で捨てたカード１枚が青で、もう１枚が白か赤か緑か黒の場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Glare At");
        setDescription("en",
                "Draw 4 cards, and discard 2 cards from your hand. If you discarded one blue card and another white, red, green, or black card, choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "怒目·圆睁");
        setDescription("zh_simplified", 
                "抽4张牌，手牌2张舍弃。这个方法舍弃的牌1张是蓝色且，另1张是白色或红色或绿色或黑色的场合，不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
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
            DataTable<CardIndex> data = discard(2);
            
            if(data.size() == 2)
            {
                CardDataColor color1 = data.get(0).getIndexedInstance().getColor();
                CardDataColor color2 = data.get(1).getIndexedInstance().getColor();
                
                if((color1.matches(CardColor.BLUE) && color2.matches(CardColor.WHITE, CardColor.RED, CardColor.GREEN, CardColor.BLACK)) ||
                   (color2.matches(CardColor.BLUE) && color1.matches(CardColor.WHITE, CardColor.RED, CardColor.GREEN, CardColor.BLACK)))
                {
                    CardIndex cardIndex = playerChoiceHand().get();
                    discard(cardIndex);
                }
            }
        }
    }
}
