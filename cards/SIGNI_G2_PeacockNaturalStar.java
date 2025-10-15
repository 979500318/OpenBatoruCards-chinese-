package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_PeacockNaturalStar extends Card {
    
    public SIGNI_G2_PeacockNaturalStar()
    {
        setImageSets("WXDi-P01-074");
        
        setOriginalName("羅星　ピーコック");
        setAltNames("ラセイピーコック Rasei Piikokku");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。その後、そのカードがレベル１のシグニの場合、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );
        
        setName("en", "Pavo, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a level one SIGNI, add up to one target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Peacock, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a level 1 SIGNI, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "罗星 孔雀座");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。然后，那张牌是等级1的精灵的场合，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);
        
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
                    CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                    addToHand(target);
                }
            }
        }
    }
}
