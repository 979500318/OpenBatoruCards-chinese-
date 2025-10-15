package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_CodeArtSKyCat extends Card {
    
    public SIGNI_K1_CodeArtSKyCat()
    {
        setImageSets("WXDi-P08-077");
        
        setOriginalName("コードアート　Ｓカイキャット");
        setAltNames("コードアートエスカイキャット Koodo Aato Esu Kai Kyatto Sky Cat");
        setDescription("jp",
                "@E @[手札からスペルを１枚捨てる]@：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで手札に加え、残りをトラッシュに置く。"
        );
        
        setName("en", "S-Kycat, Code: Art");
        setDescription("en",
                "@E @[Discard a spell]@: Look at the top three cards of your deck. Add up to one card from among them to your hand and put the rest into your trash."
        );
        
        setName("en_fan", "Code Art S Ky Cat");
        setDescription("en_fan",
                "@E @[Discard 1 spell from your hand]@: Look at the top 3 cards of your deck. Add up to 1 card from among them to your hand, and put the rest into the trash."
        );
        
		setName("zh_simplified", "必杀代号 猫爬架");
        setDescription("zh_simplified", 
                "@E 从手牌把魔法1张舍弃:从你的牌组上面看3张牌。从中把牌1张最多加入手牌，剩下的放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(new TargetFilter().spell()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            trash(getCardsInLooked(getOwner()));
        }
    }
}
