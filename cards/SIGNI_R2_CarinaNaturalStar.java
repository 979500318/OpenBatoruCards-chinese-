package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_CarinaNaturalStar extends Card {
    
    public SIGNI_R2_CarinaNaturalStar()
    {
        setImageSets("WXDi-P01-059", "SPDi01-60","SPDi38-22");
        
        setOriginalName("羅星　カリーナ");
        setAltNames("ラセイカリーナ Rasei Kariina");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。その後、そのカードがレベル１のシグニの場合、対戦相手のパワー8000以下のシグニ１体を対象とし、Colorless.pngを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Carina, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a level one SIGNI, you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Carina, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a level 1 SIGNI, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %X. If you do, banish it."
        );
        
		setName("zh_simplified", "罗星 船底座");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。然后，那张牌是等级1的精灵的场合，对战对手的力量8000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
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
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                    
                    if(target != null && payEner(Cost.colorless(1)))
                    {
                        banish(target);
                    }
                }
            }
        }
    }
}
