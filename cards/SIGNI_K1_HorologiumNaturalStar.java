package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_HorologiumNaturalStar extends Card {
    
    public SIGNI_K1_HorologiumNaturalStar()
    {
        setImageSets("WXDi-P01-082");
        
        setOriginalName("羅星　ホロロジウム");
        setAltNames("ラセイホロロジウム Rasei Hororojiumu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。その後、そのカードのレベルが１の場合、あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。この方法でシグニを手札に加えた場合、手札を１枚捨てる。"
        );
        
        setName("en", "Horologium, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a level one SIGNI, add target SIGNI without a #G from your trash to your hand. If you added a SIGNI to your hand this way, discard a card."
        );
        
        setName("en_fan", "Horologium, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a level 1 SIGNI, target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand. If you add a SIGNI to your hand this way, discard 1 card from your hand."
        );
        
		setName("zh_simplified", "罗星 时钟座 ");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的牌组最上面公开。然后，那张牌是等级1的精灵的场合，从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。这个方法把精灵加入手牌的场合，手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(cardIndex.getIndexedInstance().getLevelByRef() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                    
                    if(target != null)
                    {
                        addToHand(target);
                        discard(1);
                    }
                }
            }
        }
    }
}
