package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
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
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B3_OrionNaturalStarPrincess extends Card {
    
    public SIGNI_B3_OrionNaturalStarPrincess()
    {
        setImageSets("WXDi-P04-037");
        
        setOriginalName("羅星姫　オリオン");
        setAltNames("ラセイキオリオン Raseiki Orion");
        setDescription("jp",
                "@C：対戦相手のターンの間、対戦相手のシグニが場を離れる場合、代わりにトラッシュに置かれる。\n" +
                "@U：対戦相手のターンの終了時、カードを１枚引く。\n" +
                "@A @[手札からレベル１のシグニを１枚捨てる]@：対戦相手のすべてのシグニを凍結する。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Orion, Natural Planet Queen");
        setDescription("en",
                "@C: During your opponent's turn, if a SIGNI would leave your opponent's field, it is put into its owner's trash instead.\n" +
                "@U: At the end of your opponent's turn, draw a card.\n" +
                "@A @[Discard a level one SIGNI]@: Freeze all SIGNI on your opponent's field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Orion, Natural Star Princess");
        setDescription("en_fan",
                "@C: During your opponent's turn, whenever your opponent's SIGNI would leave the field, instead it is put into the trash.\n" +
                "@U: At the end of your opponent's turn, draw 1 card.\n" +
                "@A @[Discard 1 level 1 SIGNI from your hand]@: Freeze all of your opponent's SIGNI." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "罗星姬 猎户座");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，对战对手的精灵离场的场合，作为替代，放置到废弃区。\n" +
                "@U :对战对手的回合结束时，抽1张牌。\n" +
                "@A 从手牌把等级1的精灵1张舍弃:对战对手的全部的精灵冻结。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER,OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new DiscardCost(new TargetFilter().SIGNI().withLevel(1)), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffSharedCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return !isOwnCard(event.getCaller()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }
        
        private void onActionEff()
        {
            freeze(getSIGNIOnField(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
