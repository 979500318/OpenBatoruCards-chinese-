package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_VelaNaturalStar extends Card {
    
    public SIGNI_B3_VelaNaturalStar()
    {
        setImageSets("WXDi-P00-065");
        
        setOriginalName("羅星　ヴェラ");
        setAltNames("ラセイヴェラ Rasei Vera");
        setDescription("jp",
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );
        
        setName("en", "Vela, Natural Planet");
        setDescription("en",
                "~#Put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Vela, Natural Star");
        setDescription("en_fan",
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "罗星 船帆座");
        setDescription("zh_simplified", 
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(13000);
        
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(cardIndex, DeckPosition.BOTTOM);
        }
    }
}
