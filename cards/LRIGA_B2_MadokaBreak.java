package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_MadokaBreak extends Card {
    
    public LRIGA_B2_MadokaBreak()
    {
        setImageSets("WXDi-P01-028");
        
        setOriginalName("マドカ//ブレイク");
        setAltNames("マドカブレイク Madoka Bureiku");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とする。対戦相手は手札を２枚捨てないかぎり、それをデッキの一番下に置く。\n" +
                "@E %B %X %X：対戦相手のシグニ１体を対象とする。対戦相手は手札を２枚捨てないかぎり、それをデッキの一番下に置く。"
        );
        
        setName("en", "Madoka//Break");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field on the bottom of its owner's deck unless your opponent discards two cards.\n" +
                "@E %B %X %X: Put target SIGNI on your opponent's field on the bottom of its owner's deck unless your opponent discards two cards."
        );
        
        setName("en_fan", "Madoka//Break");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI. If your opponent doesn't discard 2 cards from their hand, put it on the bottom of their deck.\n" +
                "@E %B %X %X: Target 1 of your opponent's SIGNI. If your opponent doesn't discard 2 cards from their hand, put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "円//破碎");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象。如果对战对手不把手牌2张舍弃，那么将其放置到牌组最下面。\n" +
                "@E %B%X %X:对战对手的精灵1只作为对象。如果对战对手不把手牌2张舍弃，那么将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            if(target != null && discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).size() != 2)
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
