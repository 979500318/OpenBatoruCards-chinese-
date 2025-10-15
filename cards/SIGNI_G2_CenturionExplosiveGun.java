package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G2_CenturionExplosiveGun extends Card {
    
    public SIGNI_G2_CenturionExplosiveGun()
    {
        setImageSets("WXDi-P07-081");
        
        setOriginalName("爆砲　センチュリオン");
        setAltNames("バクホウセンチュリオン Bakuhou Senchurion");
        setDescription("jp",
                "@E #C #C：【エナチャージ１】\n" +
                "@A %G %G @[このシグニを場からトラッシュに置く]@：対戦相手のレベル３以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Centurion, Explosive Gun");
        setDescription("en",
                "@E #C #C: [[Ener Charge 1]] \n" +
                "@A %G %G @[Put this SIGNI on your field into its owner's trash]@: Vanish target level three or more SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Centurion, Explosive Gun");
        setDescription("en_fan",
                "@E #C #C: [[Ener Charge 1]]\n" +
                "@A %G %G @[Put this SIGNI from the field into the trash]@: Target 1 of your opponent's level 3 or higher SIGNI, and banish it."
        );
        
		setName("zh_simplified", "爆炮 百夫长主战坦克");
        setDescription("zh_simplified", 
                "@E #C #C:[[能量填充1]]（你的牌组最上面的牌放置到能量区）\n" +
                "@A %G %G这只精灵从场上放置到废弃区:对战对手的等级3以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new CoinCost(2), this::onEnterEff);
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.GREEN, 2)),
                new TrashCost()
            ), this::onActionEff);
        }
        
        private void onEnterEff()
        {
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3,0)).get();
            banish(target);
        }
    }
}
