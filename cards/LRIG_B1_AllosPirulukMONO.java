package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_B1_AllosPirulukMONO extends Card {

    public LRIG_B1_AllosPirulukMONO()
    {
        setImageSets("WDK02-004");

        setOriginalName("アロス・ピルルク　ＭＯＮＯ");
        setAltNames("アロスピルルクモノ Arosu Piruruku Mono");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：#Cを得る。"
        );

        setName("en", "Allos Piruluk MONO");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Gain #C."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 MOMO");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:得到#C。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
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
