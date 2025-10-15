package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_MidorikoGoldenCombatGirlTypeThree extends Card {

    public LRIG_G3_MidorikoGoldenCombatGirlTypeThree()
    {
        setImageSets("WDK03-002");

        setOriginalName("三型金闘娘　翠子");
        setAltNames("サンガタキントウキミドリコ Sangata Kintouki Midoriko");
        setDescription("jp",
                "@E %G：#Cを得る。"
        );

        setName("en", "Midoriko, Golden Combat Girl Type Three");
        setDescription("en",
                "@E %G: Gain #C."
        );

		setName("zh_simplified", "三型金斗娘 翠子");
        setDescription("zh_simplified", 
                "@E %G:得到#C。\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(7);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            gainCoins(1);
        }
    }
}
