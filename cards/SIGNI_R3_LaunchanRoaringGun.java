package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R3_LaunchanRoaringGun extends Card {
    
    public SIGNI_R3_LaunchanRoaringGun()
    {
        setImageSets("WXDi-P08-060");
        
        setOriginalName("轟砲　ランチャン");
        setAltNames("ゴウホウランチャン Gouhou Ranchan");
        setDescription("jp",
                "@A %R %R @[このシグニを場からトラッシュに置く]@：あなたの赤のシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニがアタックしたとき、このシグニの正面のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Launchan, Roaring Gun");
        setDescription("en",
                "@A %R %R @[Put this SIGNI on your field into its owner's trash]@: Target red SIGNI on your field gains@>@U $T1: When this SIGNI attacks, vanish target SIGNI in front of this SIGNI.@@until end of turn."
        );
        
        setName("en_fan", "Launchan, Roaring Gun");
        setDescription("en_fan",
                "@A %R %R @[Put this SIGNI into the trash]@: Target 1 of your red SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI attacks, target 1 SIGNI in front of this SIGNI, and banish it."
        );
        
		setName("zh_simplified", "轰炮 远射炮");
        setDescription("zh_simplified", 
                "@A %R %R这只精灵从场上放置到废弃区:你的红色的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵攻击时，这只精灵的正面的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.RED, 2)), new TrashCost()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndexSource = getAbility().getSourceCardIndex();
            
            CardIndex target = cardIndexSource.getIndexedInstance().playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().match(cardIndexSource.getIndexedInstance().getOppositeSIGNI())).get();
            cardIndexSource.getIndexedInstance().banish(target);
        }
    }
}
