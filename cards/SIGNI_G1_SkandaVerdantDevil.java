package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G1_SkandaVerdantDevil extends Card {
    
    public SIGNI_G1_SkandaVerdantDevil()
    {
        setImageSets("WXDi-P00-067");
        
        setOriginalName("翠魔　スカンダ");
        setAltNames("スイマスカンダ Suima Sukanda");
        setDescription("jp",
                "@A %G %G %X @[シグニ１体を場からトラッシュに置く]@：この方法でトラッシュに置いたシグニよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：【エナチャージ２】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );
        
        setName("en", "Skanda, Jade Evil");
        setDescription("en",
                "@A %G %G %X @[Put a SIGNI on your field into its owner's trash]@: Vanish target SIGNI on your opponent's field with power less than the power of the SIGNI put into the trash this way." +
                "~#[[Ener Charge 2]], then add up to one target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Skanda, Verdant Devil");
        setDescription("en_fan",
                "@A %G %G %X @[Put 1 SIGNI from your field into the trash]@: Target 1 of your opponent's SIGNI with power less than the SIGNI put into the trash this way, and banish it." +
                "~#[[Ener Charge 2]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "翠魔 室建陀");
        setDescription("zh_simplified", 
                "@E %G %G%X精灵1只从场上放置到废弃区:比这个方法放置到废弃区的精灵的力量低的对战对手的精灵1只作为对象，将其破坏。" +
                "~#[[能量填充2]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)),
                new TrashCost(new TargetFilter().SIGNI())
            ), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            if(!getAbility().getCostPaidData(0,1).isEmpty())
            {
                CardIndexSnapshot cardIndexTrashed = ((CardIndexSnapshot)getAbility().getCostPaidData(0,1).get());
                if(cardIndexTrashed.getSourceCardIndex().getLocation() != CardLocation.TRASH) return;
                
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, cardIndexTrashed.getPower().getValue()-1)).get();
                banish(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(2);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
