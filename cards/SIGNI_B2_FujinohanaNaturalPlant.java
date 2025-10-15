package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_B2_FujinohanaNaturalPlant extends Card {
    
    public SIGNI_B2_FujinohanaNaturalPlant()
    {
        setImageSets("WXDi-P07-074");
        
        setOriginalName("羅植　フジノハナ");
        setAltNames("ラショクフジノハナ Rashoku Fujinohana");
        setDescription("jp",
                "@E #C：【エナチャージ１】"
        );
        
        setName("en", "Wisteria, Natural Plant");
        setDescription("en",
                "@E #C: [[Ener Charge 1]] "
        );
        
        setName("en_fan", "Fujinohana, Natural Plant");
        setDescription("en_fan",
                "@E #C: [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "罗植 多花紫藤 ");
        setDescription("zh_simplified", 
                "@E #C:[[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            enerCharge(1);
        }
    }
}
