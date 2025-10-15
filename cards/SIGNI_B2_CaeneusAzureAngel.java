package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_B2_CaeneusAzureAngel extends Card {
    
    public SIGNI_B2_CaeneusAzureAngel()
    {
        setImageSets("WXDi-P07-072");
        
        setOriginalName("蒼天　カイニス");
        setAltNames("ソウテンカイニス Souten Kainisu");
        setDescription("jp",
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー3000以下のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "$$2対戦相手のパワー8000以下のシグニ１体を対象とし、#Cを支払ってもよい。そうした場合、それをデッキの一番下に置く。"
        );
        
        setName("en", "Caeneus, Azure Angel");
        setDescription("en",
                "~#Choose one --\n$$1 Put target SIGNI on your opponent's field with power 3000 or less on the bottom of its owner's deck.\n$$2 You may pay #C. If you do, put target SIGNI on your opponent's field with power 8000 or less on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Caeneus, Azure Angel");
        setDescription("en_fan",
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 3000 or less, and put it on the bottom of their deck.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 8000 or less, and you may pay #C. If you do, put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "苍天 凯涅厄斯");
        setDescription("zh_simplified", 
                "~#以下选1种。#C。这样做的场合，将其放置到牌组最下面。\n" +
                "$$1 对战对手的力量3000以下的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "$$2 对战对手的力量8000以下的精灵1只作为对象，可以支付\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(0,3000)).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(0,8000)).get();
                if(target != null && payAll(new CoinCost(1))) returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
