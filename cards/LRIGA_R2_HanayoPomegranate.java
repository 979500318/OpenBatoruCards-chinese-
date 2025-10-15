package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_R2_HanayoPomegranate extends Card {
    
    public LRIGA_R2_HanayoPomegranate()
    {
        setImageSets("WXDi-P06-026");
        
        setOriginalName("花代・柘榴");
        setAltNames("ハナヨザクロ Hanayo Zakuro");
        setDescription("jp",
                "@E：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E @[手札を４枚捨てる]@：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Hanayo, Pomegranate");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 12000 or less.\n" +
                "@E @[Discard four cards]@: Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Hanayo Pomegranate");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@E @[Discard 4 cards from your hand]@: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "花代·柘榴");
        setDescription("zh_simplified", 
                "@E :对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@E 手牌4张舍弃:对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new DiscardCost(4), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
