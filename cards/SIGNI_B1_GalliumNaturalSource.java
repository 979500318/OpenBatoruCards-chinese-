package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_GalliumNaturalSource extends Card {
    
    public SIGNI_B1_GalliumNaturalSource()
    {
        setImageSets("WXDi-P04-064", "SPDi01-100");
        
        setOriginalName("羅原　Ga");
        setAltNames("ラゲンガリウム Ragen Garium");
        setDescription("jp",
                "~#：カードを２枚引く。対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Ga, Natural Element");
        setDescription("en",
                "~#Draw two cards. Your opponent discards a card."
        );
        
        setName("en_fan", "Gallium, Natural Source");
        setDescription("en_fan",
                "~#Draw 2 cards. Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "罗原 Ga");
        setDescription("zh_simplified", 
                "~#抽2张牌。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(5000);
        
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
            draw(2);
            
            discard(getOpponent(), 1);
        }
    }
}
