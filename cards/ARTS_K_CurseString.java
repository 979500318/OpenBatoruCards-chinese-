package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;

public final class ARTS_K_CurseString extends Card {

    public ARTS_K_CurseString()
    {
        setImageSets("WX24-P4-034");

        setOriginalName("カース・ストリング");
        setAltNames("カースストリング Kaasu Sutoringu");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚トラッシュに置く。その後、この方法でトラッシュに置かれたカードの中からシグニを２枚まで対象とし、それらを手札に加える。この方法で手札に加えたカード１枚が黒で、もう１枚が白か赤か青か緑の場合、対戦相手のデッキの上からカードを８枚トラッシュに置く。"
        );

        setName("en", "Curse String");
        setDescription("en",
                "Put the top 5 cards of your deck into the trash. Then, target up to 2 SIGNI that were put into the trash this way, and add them to your hand. If you added one black card and another white, red, blue, or green card, put the top 8 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "诅咒·串联");
        setDescription("zh_simplified", 
                "从你的牌组上面把5张牌放置到废弃区。然后，从这个方法放置到废弃区的牌中把精灵2张最多作为对象，将这些加入手牌。这个方法加入手牌的牌1张是黑色且，另1张是白色或红色或蓝色或绿色的场合，从对战对手的牌组上面把8张牌放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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
            DataTable<CardIndex> dataMilled = millDeck(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash().match(dataMilled));
            boolean match = false;
            if(data.size() == 2)
            {
                CardDataColor color1 = data.get(0).getIndexedInstance().getColor();
                CardDataColor color2 = data.get(1).getIndexedInstance().getColor();
                
                match = (color1.matches(CardColor.BLACK) && color2.matches(CardColor.WHITE, CardColor.RED, CardColor.BLUE, CardColor.GREEN)) ||
                        (color2.matches(CardColor.BLACK) && color1.matches(CardColor.WHITE, CardColor.RED, CardColor.BLUE, CardColor.GREEN));
            }
            addToHand(data);
            
            if(match)
            {
                millDeck(getOpponent(), 8);
            }
        }
    }
}
