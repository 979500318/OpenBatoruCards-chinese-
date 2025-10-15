package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R3_CancerNaturalStar extends Card {
    
    public SIGNI_R3_CancerNaturalStar()
    {
        setImageSets("WXDi-P04-061");
        
        setOriginalName("羅星　キャンサー");
        setAltNames("ラセイキャンサー Rasei Kyansaa");
        setDescription("jp",
                "@E @[手札からレベル１のシグニを２枚捨てる]@：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Cancer, Natural Planet");
        setDescription("en",
                "@E @[Discard two level one SIGNI]@: Vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Cancer, Natural Star");
        setDescription("en_fan",
                "@E @[Discard 2 level 1 SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );
        
		setName("zh_simplified", "罗星 巨蟹座");
        setDescription("zh_simplified", 
                "@E 从手牌把等级1的精灵2张舍弃:对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            registerEnterAbility(new DiscardCost(2, new TargetFilter().SIGNI().withLevel(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}
