package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.actions.ActionExclude;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B2_MilulunMemoriaNaturalSource extends Card {
    
    public SIGNI_B2_MilulunMemoriaNaturalSource()
    {
        setImageSets("WXDi-P06-066", "WXDi-P06-066P");
        
        setOriginalName("羅原　ミルルン//メモリア");
        setAltNames("ラゲンミルルンメモリア Ragen Mirurun Memoria");
        setDescription("jp",
                "@E：このターン、あなたが次にスペルを使用する場合、その使用コストに含まれるエナコスト１つを選んで代わりに%Xとして支払ってもよい。\n" +
                "@A $T1 %X：対戦相手のトラッシュからスペル１枚を対象とし、それを使用する。このターン、それがチェックゾーンから別の領域に移動される場合、代わりにゲームから除外される。"
        );
        
        setName("en", "Milulun//Memoria, Natural Element");
        setDescription("en",
                "@E: The next time you use a spell this turn, you may choose an Ener cost in its use cost and instead pay %X for it. \n" +
                "@A $T1 %X: Use target spell from your opponent's trash. If it moves from the Check Zone to another Zone this turn, instead remove it from the game."
        );
        
        setName("en_fan", "Milulun//Memoria, Natural Source");
        setDescription("en_fan",
                "@E: The next time you use a spell this turn, you may pay one ener cost of your choice in its use cost with %X instead.\n" +
                "@A $T1 %X: Target 1 spell from your opponent's trash, and use it. If it would be moved from the check zone to another zone this turn, exclude it from the game instead."
        );
        
		setName("zh_simplified", "罗原 米璐璐恩//回忆");
        setDescription("zh_simplified", 
                "@E 这个回合，你下一次把魔法使用的场合，选其的使用费用含有的能量费用1点，作为替代，可以用%X作为支付。（例%B %B%R的场合，能用%B %B%X或%B%R%X:支付）\n" +
                "@A $T1 %X:从对战对手的废弃区把魔法1张作为对象，将其使用。这个回合，其从检查区往别的领域移动的场合，作为替代，从游戏除外。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private long cacheUsedSpellsCount;
        private void onEnterEff()
        {
            cacheUsedSpellsCount = getUsedSpellsCount();
            
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.DATA_COSTS_ENER_ALT_PAY, TargetFilter.HINT_OWNER_OWN, this::onAttachedConstEffModRuleCheck));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getUsedSpellsCount() == cacheUsedSpellsCount ? ConditionState.OK : ConditionState.BAD;
        }
        private DataTable<String> onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            EnerCost originalCost = (EnerCost)data.getGenericData(0);
            if(!(originalCost.getSourceAbility() instanceof SpellAbility) || originalCost.isEffect()) return null;
            
            String originalCostString = originalCost.getCostString().getValue();
            String originalCostStringUnique = Cost.getUniqueCostString(originalCostString);
            
            DataTable<String> dataAltPayCostStrings = new DataTable<>();
            for(char c : originalCostStringUnique.toCharArray())
            {
                if(c == CardColor.COLORLESS.getShortLabel()) continue;
                dataAltPayCostStrings.add(originalCostString.replaceFirst(String.valueOf(c), Cost.colorless(1)));
            }
            
            return dataAltPayCostStrings;
        }
        private int getUsedSpellsCount()
        {
            return GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.ABILITY &&
                    event.getSourceAbility() instanceof SpellAbility &&
                    isOwnCard(event.getCaller()));
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ACTIVATE).OP().spell().fromTrash()).get();
            
            if(use(getOwner(), target))
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().fromCheckZone().match(target), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
                ));
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionExclude(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}

