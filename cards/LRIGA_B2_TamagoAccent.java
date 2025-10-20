package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_TamagoAccent extends Card {
    
    public LRIGA_B2_TamagoAccent()
    {
        setImageSets("WXDi-P03-026");
        
        setOriginalName("タマゴ＝アクセント");
        setAltNames("タマゴアクセント Tamago Akusento");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E %B %B %B %B %X %X：対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );
        
        setName("en", "Tamago =Accents=");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field.\n" +
                "@E %B %B %B %B %X %X: Put target SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Tamago-Accent");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it.\n" +
                "@E %B %B %B %B %X %X: Target 1 of your opponent's SIGNI, and put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "玉子=强音");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其横置。\n" +
                "@E %B %B %B %B%X %X:对战对手的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 4) + Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
