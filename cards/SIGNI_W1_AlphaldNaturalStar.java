package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_AlphaldNaturalStar extends Card {
    
    public SIGNI_W1_AlphaldNaturalStar()
    {
        setImageSets("WXDi-P01-048", "SPDi38-21");
        
        setOriginalName("羅星　アルファルド");
        setAltNames("ラセイアルファルド Rasei Arufarudo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。そのカードがレベル１のシグニの場合、カードを１枚引く。"
        );
        
        setName("en", "Alphard, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If that card is a level one SIGNI, draw a card."
        );
        
        setName("en_fan", "Alphald, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a level 1 SIGNI, draw 1 card."
        );
        
		setName("zh_simplified", "罗星 长蛇的心");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。那张牌是等级1的精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(1000);
        
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
            
            if(cardIndex == null || cardIndex.getIndexedInstance().getLevelByRef() != 1 ||
               draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
