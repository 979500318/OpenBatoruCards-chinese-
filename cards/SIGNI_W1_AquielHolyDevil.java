package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W1_AquielHolyDevil extends Card {
    
    public SIGNI_W1_AquielHolyDevil()
    {
        setImageSets("WXDi-P03-048");
        
        setOriginalName("聖魔　アクィエル");
        setAltNames("セイマアクィエル Seima Akuuieru");
        setDescription("jp",
                "@E %X：あなたのデッキの上からカードを３枚見る。その中から＜悪魔＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Aquiel, Blessed Evil");
        setDescription("en",
                "@E %X: Look at the top three cards of your deck. Reveal a <<Demon>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Aquiel, Holy Devil");
        setDescription("en_fan",
                "@E %X: Look at the top 3 cards of your deck. Reveal 1 <<Devil>> SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "圣魔 阿奎尔");
        setDescription("zh_simplified", 
                "@E %X:从你的牌组上面看3张牌。从中把<<悪魔>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
