package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_RyugutsukaWaterPhantom extends Card {
    
    public SIGNI_B1_RyugutsukaWaterPhantom()
    {
        setImageSets("WXDi-P01-064");
        
        setOriginalName("幻水　リュグツカ");
        setAltNames("ゲンスイリュグツカ Gensui Ryugutsuka");
        setDescription("jp",
                "@E：カードを１枚引き、手札を１枚捨てる。" +
                "~#：カードを２枚引く。"
        );
        
        setName("en", "Regalecus, Phantom Aquatic Beast");
        setDescription("en",
                "@E: Draw a card and discard a card." +
                "~#Draw two cards."
        );
        
        setName("en_fan", "Ryugutsuka, Water Phantom");
        setDescription("en_fan",
                "@E: Draw 1 card, and discard 1 card from your hand." +
                "~#Draw 2 cards."
        );
        
		setName("zh_simplified", "幻水 龙殿 ");
        setDescription("zh_simplified", 
                "@E :抽1张牌，手牌1张舍弃。" +
                "~#抽2张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
            discard(1);
        }
        
        private void onLifeBurstEff()
        {
            draw(2);
        }
    }
}
