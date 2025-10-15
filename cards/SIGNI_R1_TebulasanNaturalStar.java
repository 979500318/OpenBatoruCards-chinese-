package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_R1_TebulasanNaturalStar extends Card {
    
    public SIGNI_R1_TebulasanNaturalStar()
    {
        setImageSets("WXDi-P02-058");
        
        setOriginalName("羅星　テブルサン");
        setAltNames("ラセイテブルサン Rasei Teburusan");
        setDescription("jp",
                "@A #D：このシグニをデッキの一番上に置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Mensah, Natural Planet");
        setDescription("en",
                "@A #D: Put this SIGNI on the top of its owner's deck." +
                "~#You may pay %R. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Tebulasan, Natural Star");
        setDescription("en_fan",
                "@A #D: Return this SIGNI to the top of your deck." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %R. If you do, banish it."
        );
        
		setName("zh_simplified", "罗星 山案座 ");
        setDescription("zh_simplified", 
                "@A #D:这只精灵放置到牌组最上面。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(new DownCost()), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            returnToDeck(getCardIndex(), DeckPosition.TOP);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
