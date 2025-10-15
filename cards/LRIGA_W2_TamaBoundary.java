package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_W2_TamaBoundary extends Card {
    
    public LRIGA_W2_TamaBoundary()
    {
        setImageSets("WXDi-P08-025");
        
        setOriginalName("タマ・ばうんだり");
        setAltNames("タマバウンダリー Tama Baundarii");
        setDescription("jp",
                "@E：数字１つを宣言する。その後、あなたのデッキの上からカードを３枚公開する。その中に宣言した数字と同じレベルのシグニがある場合、対戦相手のシグニ１体を対象とし、それを手札に戻す。この効果で公開したカードを好きな順番でデッキの一番上に戻す。"
        );
        
        setName("en", "Tama Boundary");
        setDescription("en",
                "@E: Declare a number. Then, reveal the top three cards of your deck. If there is a SIGNI among them that is the same level as the declared number, return target SIGNI on your opponent's field to its owner's hand. Put cards revealed with this effect on top of your deck in any order."
        );
        
        setName("en_fan", "Tama Boundary");
        setDescription("en_fan",
                "@E: Declare 1 number. Then, reveal the top 3 cards of your deck. If there is a SIGNI with the same level as the declared number among them, target 1 of your opponent's SIGNI, and return it to their hand. Return the cards revealed by this effect to the top of your deck in any order."
        );
        
		setName("zh_simplified", "小玉·圣界");
        setDescription("zh_simplified", 
                "@E :数字1种宣言。然后，从你的牌组上面把3张牌公开。从中有与宣言数字相同等级的精灵的场合，对战对手的精灵1只作为对象，将其返回手牌。这个效果公开的牌任意顺序返回牌组最上面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            int number = playerChoiceNumber(0,1,2,3,4,5) - 1;
            
            reveal(3);
            
            if(new TargetFilter().own().SIGNI().fromRevealed().withLevel(number).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
            
            while(getRevealedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromRevealed()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
