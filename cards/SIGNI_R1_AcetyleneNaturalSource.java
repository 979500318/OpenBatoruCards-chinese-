package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R1_AcetyleneNaturalSource extends Card {
    
    public SIGNI_R1_AcetyleneNaturalSource()
    {
        setImageSets("WXDi-P01-056");
        
        setOriginalName("羅原　Ｃ２Ｈ２");
        setAltNames("ラゲンアセチレン Ragen Asechiren C2H2");
        setDescription("jp",
                "@A %X %X %X @[このシグニを場からトラッシュに置く]@：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー１２０００以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "C2H2, Natural Element");
        setDescription("en",
                "@A %X %X %X @[Put this SIGNI on your field into its owner's trash]@: Vanish target SIGNI on your opponent's field with power 12000 or less." +
                "~#You may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Acetylene, Natural Source");
        setDescription("en_fan",
                "@A %X %X %X @[Put this SIGNI from the field into the trash]@: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "罗原 C2H2");
        setDescription("zh_simplified", 
                "@A %X %X %X这只精灵从场上放置到废弃区:对战对手的力量12000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(3)), new TrashCost()), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
