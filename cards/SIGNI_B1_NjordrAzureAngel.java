package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_NjordrAzureAngel extends Card {
    
    public SIGNI_B1_NjordrAzureAngel()
    {
        setImageSets("WXDi-P00-058");
        
        setOriginalName("蒼天　ニョルズ");
        setAltNames("ソウテンニョルズ Sooten Nyoruzu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、カードを１枚引き、手札を１枚捨てる。"
        );
        
        setName("en", "Njord, Azure Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, draw a card and discard a card."
        );
        
        setName("en_fan", "Njörðr, Azure Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, draw 1 card, and discard 1 card from your hand."
        );
        
		setName("zh_simplified", "苍天 尼约德");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，抽1张牌，手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            draw(1);
            discard(1);
        }
    }
}
