package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_W3_CodeArtCHandelier extends Card {
    
    public SIGNI_W3_CodeArtCHandelier()
    {
        setImageSets("WXDi-D04-016", Mask.IGNORE+"PR-Di054");
        
        setOriginalName("コードアート　Ｃヤンデリア");
        setAltNames("コードアートシーヤンデリア Koodo Aato Shii Yanderia Chandelier");
        setDescription("jp",
                "@C：対戦相手のスペルの使用コストは%X増える。\n" +
                "@C：あなたのトラッシュにスペルがあるかぎり、このシグニのパワーは＋3000され、このシグニによってバニッシュされたシグニはエナゾーンに置かれる代わりにトラッシュに置かれる。" +
                "~#：あなたのトラッシュからシグニとスペルをそれぞれ１枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Chandelier, Code: Art");
        setDescription("en",
                "@C: Use costs of your opponent's spells are increased by %X.\n" +
                "@C: As long as there is a spell in your trash, this SIGNI gets +3000 power, and any SIGNI vanished by this SIGNI is put into the trash instead of the Ener Zone." +
                "~#Add up to one target SIGNI and one target spell from your trash to your hand."
        );
        
        setName("en_fan", "Code Art C Handelier");
        setDescription("en_fan",
                "@C: The use costs of your opponent's spells are increased by %X.\n" +
                "@C: As long as there is a spell in your trash, this SIGNI gets +3000 power, and SIGNI banished by this SIGNI are put into the trash instead of the ener zone." +
                "~#Target up to 1 SIGNI and spell each from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "必杀代号 枝形吊灯");
        setDescription("zh_simplified", 
                "@C :对战对手的魔法的使用费用增%X。\n" +
                "@C :你的废弃区有魔法时，这只精灵的力量+3000，因为这只精灵被破坏的精灵放置到能量区，作为替代，放置到废弃区。" +
                "~#从你的废弃区把精灵和魔法各1张最多作为对象，将这些加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerConstantAbility(new TargetFilter().OP().spell().anyLocation(),
                new CostModifier(this::onConstEffSharedModGetSample, ModifierMode.INCREASE)
            );
            
            registerConstantAbility(this::onConstEff2Cond, new PowerModifier(3000), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE,OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideHandler))
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private AbilityCost onConstEffSharedModGetSample()
        {
            return new EnerCost(Cost.colorless(1));
        }
        
        private ConditionState onConstEff2Cond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = new DataTable<>();
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            if(target != null) data.add(target);
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromTrash()).get();
            if(target != null) data.add(target);
            
            addToHand(data);
        }
    }
}
