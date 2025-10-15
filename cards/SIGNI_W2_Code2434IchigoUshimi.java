package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W2_Code2434IchigoUshimi extends Card {
    
    public SIGNI_W2_Code2434IchigoUshimi()
    {
        setImageSets("WXDi-D02-21");
        
        setOriginalName("コード２４３４　宇志海いちご");
        setAltNames("コードニジサンジウシミイチゴ Koodo Nijisanji Ushimi Ichigo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。そのカードが＜バーチャル＞のシグニの場合、カードを１枚引く。"
        );
        
        setName("en", "Ichigo Ushimi, Code 2434");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If that card is a <<Virtual>> SIGNI, draw a card."
        );
        
        setName("en_fan", "Code 2434 Ichigo Ushimi");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a <<Virtual>> SIGNI, draw 1 card."
        );
        
		setName("zh_simplified", "2434代号 宇志海莓");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。那张牌是<<バーチャル>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL) ||
               draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
