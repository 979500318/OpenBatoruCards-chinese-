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

public final class ARTS_G_InvitationToBeautysExhibition extends Card {

    public ARTS_G_InvitationToBeautysExhibition()
    {
        setImageSets("WX24-P4-032");

        setOriginalName("花枝招展");
        setAltNames("プラントピクチャー Puranto Pikuchaa Plant Picture");
        setDescription("jp",
                "あなたのトラッシュから#Gを持たないカードを２枚まで対象とし、それらをエナゾーンに置く。この方法でエナゾーンに置いたカード１枚が緑で、もう１枚が白か赤か青か黒の場合、カードを２枚引く。"
        );

        setName("en", "Invitation to Beauty's Exhibition");
        setDescription("en",
                "Target up to 2 cards without #G @[Guard]@ from your trash, and put them into the ener zone. If you placed one green card and another white, red, blue, or black card, draw 2 cards."
        );

		setName("zh_simplified", "花枝招展");
        setDescription("zh_simplified", 
                "从你的废弃区把不持有#G的牌2张最多作为对象，将这些放置到能量区。这个方法放置到能量区的牌1张是绿色且，另1张是白色或红色或蓝色或黑色的场合，抽2张牌。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().not(new TargetFilter().guard()).fromTrash());
            putInEner(data);
            
            if(data.size() == 2)
            {
                CardDataColor color1 = data.get(0).getIndexedInstance().getColor();
                CardDataColor color2 = data.get(1).getIndexedInstance().getColor();
                
                if((color1.matches(CardColor.GREEN) && color2.matches(CardColor.WHITE, CardColor.RED, CardColor.BLUE, CardColor.BLACK)) ||
                   (color2.matches(CardColor.GREEN) && color1.matches(CardColor.WHITE, CardColor.RED, CardColor.BLUE, CardColor.BLACK)))
                {
                    draw(2);
                }
            }
        }
    }
}
