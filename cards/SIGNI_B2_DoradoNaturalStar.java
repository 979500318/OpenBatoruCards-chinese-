package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_DoradoNaturalStar extends Card {
    
    public SIGNI_B2_DoradoNaturalStar()
    {
        setImageSets("WXDi-P02-069");
        
        setOriginalName("羅星　ドラド");
        setAltNames("ラセイドラド Rasei Dorado");
        setDescription("jp",
                "~#：カードを２枚引く。対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Dorado, Natural Planet");
        setDescription("en",
                "~#Draw two cards. Your opponent discards a card."
        );
        
        setName("en_fan", "Dorado, Natural Star");
        setDescription("en_fan",
                "~#Draw 2 cards. Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "罗星 旗鱼座");
        setDescription("zh_simplified", 
                "~#抽2张牌。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
