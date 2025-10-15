package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_G1_MidorikoGoldenGirlTypeOne extends Card {

    public LRIG_G1_MidorikoGoldenGirlTypeOne()
    {
        setImageSets("WDK03-004");

        setOriginalName("一型金娘　翠子");
        setAltNames("イチガタキンキミドリコ Ichigata Kinki Midoriko");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：#Cを得る。"
        );

        setName("en", "Midoriko, Golden Girl Type One");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Gain #C."
        );

		setName("zh_simplified", "一型金娘 翠子");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:得到#C。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            gainCoins(1);
        }
    }
}
