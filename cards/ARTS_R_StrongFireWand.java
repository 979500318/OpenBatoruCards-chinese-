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

public final class ARTS_R_StrongFireWand extends Card {

    public ARTS_R_StrongFireWand()
    {
        setImageSets("WX24-P4-028");

        setOriginalName("火剛杖");
        setAltNames("カゴウヅエ Kagouzue");
        setDescription("jp",
                "手札を２枚捨て、カードを４枚引く。その後、この方法で捨てたカード１枚が赤で、もう１枚が白か青か緑か黒の場合、対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Strong Fire Wand");
        setDescription("en",
                "Discard 2 cards from your hand, and draw 4 cards. Then, if you discarded a red card and another white, blue, green, or black card this way, target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "火刚杖");
        setDescription("zh_simplified", 
                "手牌2张舍弃，抽4张牌。然后，这个方法舍弃的牌1张是红色且，另1张是白色或蓝色或绿色或黑色的场合，对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
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
            DataTable<CardIndex> data = discard(2);
            draw(4);
            
            if(data.size() == 2)
            {
                CardDataColor color1 = data.get(0).getIndexedInstance().getColor();
                CardDataColor color2 = data.get(1).getIndexedInstance().getColor();
                
                if((color1.matches(CardColor.RED) && color2.matches(CardColor.WHITE, CardColor.BLUE, CardColor.GREEN, CardColor.BLACK)) ||
                   (color2.matches(CardColor.RED) && color1.matches(CardColor.WHITE, CardColor.BLUE, CardColor.GREEN, CardColor.BLACK)))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, 10000)).get();
                    banish(target);
                }
            }
        }
    }
}
