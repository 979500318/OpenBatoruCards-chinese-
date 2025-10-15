package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_CodeAntiBigfoot extends Card {
    
    public SIGNI_W2_CodeAntiBigfoot()
    {
        setImageSets("WXDi-P05-050");
        
        setOriginalName("コードアンチ　ビッグフット");
        setAltNames("コードアンチビッグフット Koodo Anchi Biggufuto");
        setDescription("jp",
                "@E %W：あなたのデッキの上からカードを３枚を見る。その中から白のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n\n" +
                "@C：このカードの上にあるシグニのパワーを＋1000する。"
        );
        
        setName("en", "Bigfoot, Code: Anti");
        setDescription("en",
                "@E %W: Look at the top three cards of your deck. Reveal a white SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order.\n\n" +
                "@C: The SIGNI on top of this card gets +1000 power."
        );
        
        setName("en_fan", "Code Anti Bigfoot");
        setDescription("en_fan",
                "@E %W: Look at the top 3 cards of your deck. Reveal 1 white SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order.\n\n" +
                "@C: The SIGNI on top of this card gets +1000 power."
        );
        
		setName("zh_simplified", "古兵代号 大脚怪");
        setDescription("zh_simplified", 
                "@E %W:从你的牌组上面看3张牌。从中把白色的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@C :这张牌的上面的精灵的力量+1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().over(cardId), new PowerModifier(1000));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.WHITE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
