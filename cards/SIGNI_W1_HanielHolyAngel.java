package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W1_HanielHolyAngel extends Card {
    
    public SIGNI_W1_HanielHolyAngel()
    {
        setImageSets("WXDi-P01-045", "SPDi01-98");
        
        setOriginalName("聖天　ハニエル");
        setAltNames("セイテンハニエル Seiten Hanieru");
        setDescription("jp",
                "@E %X：あなたのデッキの上からカードを３枚見る。その中からレベル２以下のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Haniel, Blessed Angel");
        setDescription("en",
                "@E %X: Look at the top three cards of your deck. Reveal a level two or less SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Haniel, Holy Angel");
        setDescription("en_fan",
                "@E %X: Look at the top 3 cards of your deck. Reveal 1 level 2 or lower SIGNI from among them, and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "圣天 汉尼尔");
        setDescription("zh_simplified", 
                "@E %X:从你的牌组上面看3张牌。从中把等级2以下的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(0,2).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
