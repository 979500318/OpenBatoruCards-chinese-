package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class SIGNI_B2_MuscariNaturalPlant extends Card {
    
    public SIGNI_B2_MuscariNaturalPlant()
    {
        setImageSets("WXDi-P05-065");
        
        setOriginalName("羅植　ムスカリ");
        setAltNames("ラショクムスカリ Rashoku Musukari");
        setDescription("jp",
                "~#：対戦相手のシグニ１体を対象とし、手札を２枚捨ててもよい。そうした場合、それをデッキの一番下に置く。"
        );
        
        setName("en", "Muscari, Natural Plant");
        setDescription("en",
                "~#You may discard two cards. If you do, put target SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Muscari, Natural Plant");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI, and you may discard 2 cards from your hand. If you do, put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "罗植 葡萄风信子");
        setDescription("zh_simplified", 
                "~#对战对手的精灵1只作为对象，可以把手牌2张舍弃。这样做的场合，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            
            if(target != null && discard(0,2, ChoiceLogic.BOOLEAN).size() == 2)
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
