package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_CarneliNaturalStone extends Card {
    
    public SIGNI_R3_CarneliNaturalStone()
    {
        setImageSets("WXDi-P00-056");
        
        setOriginalName("羅石　カーネリー");
        setAltNames("ラセキカーネリー Raseki Kaanerii");
        setDescription("jp",
                "@E %X %X：あなたのデッキの上からカードを３枚見る。その中から赤のシグニ１枚を探して公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Nelian, Natural Crystal");
        setDescription("en",
                "@E %X %X: Look at the top three cards of your deck. Reveal a red SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Carneli, Natural Stone");
        setDescription("en_fan",
                "@E %X %X: Look at the top 3 cards of your deck. Reveal 1 red SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "罗石 红玉");
        setDescription("zh_simplified", 
                "@E %X %X:从你的牌组上面看3张牌。从中把红色的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
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
            
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.RED).fromLooked()).get();
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(cardIndex);
        }
    }
}
