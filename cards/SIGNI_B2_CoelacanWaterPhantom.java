package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_CoelacanWaterPhantom extends Card {
    
    public SIGNI_B2_CoelacanWaterPhantom()
    {
        setImageSets("WXDi-P01-067");
        
        setOriginalName("幻水　シィラカン");
        setAltNames("ゲンスイシィラカン Gensui Shiirakan");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、カードを１枚引く。"
        );
        
        setName("en", "Coelacanth, Phantom Aquatic Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, draw a card."
        );
        
        setName("en_fan", "Coelacan, Water Phantom");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, draw 1 card."
        );
        
		setName("zh_simplified", "幻水 鲯鳅妹");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            draw(1);
        }
    }
}
