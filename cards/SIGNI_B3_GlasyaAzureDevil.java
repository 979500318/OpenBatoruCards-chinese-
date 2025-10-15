package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_B3_GlasyaAzureDevil extends Card {
    
    public SIGNI_B3_GlasyaAzureDevil()
    {
        setImageSets("WXDi-P04-069");
        
        setOriginalName("蒼魔　グラシャラ");
        setAltNames("ソウマグラシャラ Souma Gurashara");
        setDescription("jp",
                "@A @[このシグニを場からトラッシュに置く]@：対戦相手のシグニを２体まで対象とし、それらを凍結する。\n" +
                "@A %B %X @[このシグニを場からトラッシュに置く]@：対戦相手の手札が０枚の場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Glasya, Azure Evil");
        setDescription("en",
                "@A @[Put this SIGNI on your field into its owner's trash]@: Freeze up to two target SIGNI on your opponent's field.\n" +
                "@A %B %X @[Put this SIGNI on your field into its owner's trash]@: If your opponent has no cards in their hand, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Glasya, Azure Devil");
        setDescription("en_fan",
                "@A @[Put this SIGNI into the trash]@: Target up to 2 of your opponent's SIGNI, and freeze them.\n" +
                "@A %B %X @[Put this SIGNI into the trash]@: If your opponent has 0 cards in their hand, target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "苍魔 格莱杨拉波尔");
        setDescription("zh_simplified", 
                "@A 这只精灵从场上放置到废弃区:对战对手的精灵2只最多作为对象，将这些冻结。\n" +
                "@A %B%X这只精灵从场上放置到废弃区:对战对手的手牌在0张的场合，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new TrashCost(), this::onActionEff1);
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)), new TrashCost()), this::onActionEff2);
        }
        
        private void onActionEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FREEZE).OP().SIGNI());
            freeze(data);
        }
        
        private void onActionEff2()
        {
            if(getHandCount(getOpponent()) == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            }
        }
    }
}
