package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_MandrillPhantomBeast extends Card {
    
    public SIGNI_R3_MandrillPhantomBeast()
    {
        setImageSets("WXDi-P03-062");
        
        setOriginalName("幻獣　マンドリル");
        setAltNames("ゲンジュウマンドリル Genjuu Mandoriru");
        setDescription("jp",
                "@A %R %X #D：このシグニのパワーが12000以上の場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Mandrill, Phantom Terra Beast");
        setDescription("en",
                "@A %R %X #D: If this SIGNI's power is 12000 or more, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Mandrill, Phantom Beast");
        setDescription("en_fan",
                "@A %R %X #D: If this SIGNI's power is 12000 or more, target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "幻兽 山魈");
        setDescription("zh_simplified", 
                "@A %R%X横置:这只精灵的力量在12000以上的场合，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), new DownCost()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            if(getPower().getValue() >= 12000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            }
        }
    }
}
