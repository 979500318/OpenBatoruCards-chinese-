package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W2_CircinusNaturalStar extends Card {
    
    public SIGNI_W2_CircinusNaturalStar()
    {
        setImageSets("WXDi-P02-051");
        
        setOriginalName("羅星　キルキヌス");
        setAltNames("ラセイキルキヌス Rasei Kirukinusu");
        setDescription("jp",
                "@E %X：あなたのデッキの上からカードを５枚見る。その中からレベル１のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Circinus, Natural Planet");
        setDescription("en",
                "@E %X: Look at the top five cards of your deck. Reveal a level one SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Circinus, Natural Star");
        setDescription("en_fan",
                "@E %X: Look at the top 5 cards of your deck. Reveal 1 level 1 SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "罗星 圆规座");
        setDescription("zh_simplified", 
                "@E %X:从你的牌组上面看5张牌。从中把等级1的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(7000);
        
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
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(1).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
