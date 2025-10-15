package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G3_BlacksaddleFilefishWaterPhantom extends Card {
    
    public SIGNI_G3_BlacksaddleFilefishWaterPhantom()
    {
        setImageSets("WXDi-P00-071");
        
        setOriginalName("翠魔　ブエル");
        setAltNames("スイマブエル Suima Bueru");
        setDescription("jp",
                "@E：[[エナチャージ１]]"
        );
        
        setName("en", "Blacksaddle, Phantom Aquatic Beast");
        setDescription("en",
                "@E: [[Ener Charge 1]]"
        );
        
        setName("en_fan", "Blacksaddle Filefish, Water Phantom");
        setDescription("en_fan",
                "@E: [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "幻水 副革单棘鲀");
        setDescription("zh_simplified", 
                "@E :[[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            enerCharge(1);
        }
    }
}
