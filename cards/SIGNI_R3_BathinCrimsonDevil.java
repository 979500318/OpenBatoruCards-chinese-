package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R3_BathinCrimsonDevil extends Card {
    
    public SIGNI_R3_BathinCrimsonDevil()
    {
        setImageSets("WXDi-P04-060");
        
        setOriginalName("紅魔　バティン");
        setAltNames("コウマバティン Kouma Batin");
        setDescription("jp",
                "@A $T1 %X @[他の＜悪魔＞のシグニ１体を場からトラッシュに置く]@：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Bathin, Crimson Evil");
        setDescription("en",
                "@A $T1 %X @[Put another <<Demon>> SIGNI on your field into its owner's trash]@: Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Bathin, Crimson Devil");
        setDescription("en_fan",
                "@A $T1 %X @[Put 1 of your other <<Devil>> SIGNI into the trash]@: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "红魔 巴钦");
        setDescription("zh_simplified", 
                "@A $T1 %X其他的<<悪魔>>精灵1只从场上放置到废弃区:对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.colorless(1)),
                new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL).except(cardId))
            ), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
