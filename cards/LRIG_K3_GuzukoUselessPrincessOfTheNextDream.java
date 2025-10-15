package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_GuzukoUselessPrincessOfTheNextDream extends Card {
    
    public LRIG_K3_GuzukoUselessPrincessOfTheNextDream()
    {
        setImageSets("WX18-023", "WDK04-002");
        
        setOriginalName("来夢の駄姫　グズ子");
        setAltNames("ライムノダキグズコ Raimu no Daki Guzuko");
        setDescription("jp",
                "@E %K：#Cを得る。"
        );
        
        setName("en", "Guzuko, Useless Princess of the Next Dream");
        setDescription("en",
                "@E %K: Gain #C."
        );
        
		setName("zh_simplified", "来梦的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@E %K:得到#C。\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            gainCoins(1);
        }
    }
}

