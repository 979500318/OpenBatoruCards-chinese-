package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B1_MadokaSlide extends Card {
    
    public LRIGA_B1_MadokaSlide()
    {
        setImageSets("WXDi-P01-027");
        
        setOriginalName("マドカ／／スライド");
        setAltNames("マドカスライド Madoka Suraido");
        setDescription("jp",
                "@E %X：対戦相手のレベル２以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );
        
        setName("en", "Madoka//Slide");
        setDescription("en",
                "@E %X: Put target level two or less SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Madoka//Slide");
        setDescription("en_fan",
                "@E %X: Put 1 of your opponent's level 2 or lower SIGNI on the bottom of their deck."
        );
        
		setName("zh_simplified", "円//滑动");
        setDescription("zh_simplified", 
                "@E %X:对战对手的等级2以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withLevel(0,2)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
