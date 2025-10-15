package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class LRIGA_B2_ReiAbsoluteZero extends Card {
    
    public LRIGA_B2_ReiAbsoluteZero()
    {
        setImageSets("WXDi-P01-013");
        
        setOriginalName("レイ＊絶対零度");
        setAltNames("レイゼッタイレイド Rei Zettaireido");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをデッキの一番上か一番下に置く。それがレベル２以下のシグニの場合、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Rei*Absolute Zero");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field on the top or bottom of its owner's deck. If that SIGNI is level two or less, your opponent discards a card."
        );
        
        setName("en_fan", "Rei*Absolute Zero");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and return it to the top or bottom of their deck. Then, if that SIGNI was level 2 or lower, your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "令＊绝对零度");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其放置到牌组最上面或最下面。其是等级2以下的精灵的场合，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(3));
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
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).OP().SIGNI()).get();
            if(target != null)
            {
                int level = target.getIndexedInstance().getLevel().getValue();
                
                if(playerChoiceAction(ActionHint.TOP, ActionHint.BOTTOM) == 1)
                {
                    returnToDeck(target, DeckPosition.TOP);
                } else {
                    returnToDeck(target, DeckPosition.BOTTOM);
                }
                
                if(level <= 2)
                {
                    discard(getOpponent(), 1);
                }
            }
        }
    }
}
