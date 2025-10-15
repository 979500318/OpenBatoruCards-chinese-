package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CoinCost;

public final class LRIG_K2_GuzukoUselessPrincessOfMercy extends Card {
    
    public LRIG_K2_GuzukoUselessPrincessOfMercy()
    {
        setImageSets("WDK04-003");
        
        setOriginalName("蒙昧の駄姫　グズ子");
        setAltNames("モウマイノダキグズコ Moumai no Daki Guzuko");
        setDescription("jp",
                "@E #C：カードを１枚引く。"
        );
        
        setName("en", "Guzuko, Useless Princess of Mercy");
        setDescription("en",
                "@E #C: Draw 1 card."
        );
        
		setName("zh_simplified", "蒙昧的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@E #C:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(4);
        
        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new CoinCost(1), this::onEnterEff);
        }

        private void onEnterEff()
        {
            draw(1);
        }
    }
}
