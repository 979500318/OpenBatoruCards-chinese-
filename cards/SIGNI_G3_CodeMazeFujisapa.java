package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G3_CodeMazeFujisapa extends Card {
    
    public SIGNI_G3_CodeMazeFujisapa()
    {
        setImageSets("WXDi-P02-078");
        
        setOriginalName("コードメイズ　フジサパ");
        setAltNames("コードメイズフジサパ Koodo Meizu Fujisapa");
        setDescription("jp",
                "@A %G %X %X：対戦相手のパワー10000以上のシグニ１体を対象とし、それとこのシグニをデッキの一番下に置く。"
        );
        
        setName("en", "Fujisapa, Code: Maze");
        setDescription("en",
                "@A %G %X %X: Put this SIGNI and target SIGNI on your opponent's field with power 10000 or more on the bottom of their owner's deck."
        );
        
        setName("en_fan", "Code Maze Fujisapa");
        setDescription("en_fan",
                "@A %G %X %X: Target 1 of your opponent's SIGNI with power 10000 or more, and put it and this SIGNI on the bottom of the deck."
        );
        
		setName("zh_simplified", "迷宫代号 富士野生动物园");
        setDescription("zh_simplified", 
                "@A %G%X %X:对战对手的力量10000以上的精灵1只作为对象，将其与这只精灵放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(10000,0)).get();
            
            if(target != null)
            {
                returnToDeck(target, DeckPosition.BOTTOM);
                returnToDeck(getCardIndex(), DeckPosition.BOTTOM);
            }
        }
    }
}
