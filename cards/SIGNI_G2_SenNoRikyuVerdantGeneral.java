package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_SenNoRikyuVerdantGeneral extends Card {
    
    public SIGNI_G2_SenNoRikyuVerdantGeneral()
    {
        setImageSets("WXDi-D01-013");
        
        setOriginalName("翠将　センノリキュウ");
        setAltNames("スイショウセンノリキュウ Suishoo Sen no Rikyuu");
        setDescription("jp",
                "~#：[[エナチャージ３]]"
        );
        
        setName("en", "Sen no Rikyu, Jade General");
        setDescription("en",
                "~#[[Ener Charge 3]]"
        );
        
        setName("en_fan", "Sen no Rikyu, Verdant General");
        setDescription("en_fan",
                "~#[[Ener Charge 3]]"
        );
        
		setName("zh_simplified", "翠将 千利休");
        setDescription("zh_simplified", 
                "~#[[能量填充3]]（从你的牌组上面把3张牌放置到能量区）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(3);
        }
    }
}
