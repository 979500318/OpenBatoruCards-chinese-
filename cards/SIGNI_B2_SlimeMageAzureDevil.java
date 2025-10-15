package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_B2_SlimeMageAzureDevil extends Card {
    
    public SIGNI_B2_SlimeMageAzureDevil()
    {
        setImageSets("WXDi-P00-062");
        
        setOriginalName("蒼魔　スライムメイジ");
        setAltNames("ソウマスライムメイジ Souma Suraimu Meiji");
        setDescription("jp",
                "@E %B %B %X @[他のシグニ２体を場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。" +
                "~#：カードを２枚引く。"
        );
        
        setName("en", "Slime Mage, Azure Evil");
        setDescription("en",
                "@E %B %B %X @[Put two other SIGNI on your field into their owner's trash]@: Put target SIGNI on your opponent's field on the bottom of its owner's deck." +
                "~#Draw two cards."
        );
        
        setName("en_fan", "Slime Mage, Azure Devil");
        setDescription("en_fan",
                "@E %B %B %X @[Put 2 other SIGNI from your field into the trash]@: Target 1 of your opponent's SIGNI, and put it on the bottom of their deck." +
                "~#Draw 2 cards."
        );
        
		setName("zh_simplified", "苍魔 史莱姆法师");
        setDescription("zh_simplified", 
                "@E %B %B%X其他的精灵2只从场上放置到废弃区:对战对手的精灵1只作为对象，将其放置到牌组最下面。" +
                "~#抽2张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLUE, 2) + Cost.colorless(1)),
                new TrashCost(2, new TargetFilter().own().SIGNI().except(getCardIndex()))
            ), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(cardIndex, DeckPosition.BOTTOM);
        }
        
        private void onLifeBurstEff()
        {
            draw(2);
        }
    }
}
