package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.actions.ActionDown;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.SIGNIZoneRuleCheck.SIGNIZonePositionGroup;
import open.batoru.core.gameplay.rulechecks.SIGNIZoneRuleCheck.SIGNIZonePositionGroup.SIGNIZonePositionState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W2_PeroroDoll extends Card {
    
    public SIGNI_W2_PeroroDoll()
    {
        setImageSets("WXDi-CP02-TK01A");
        
        setOriginalName("ペロロ人形");
        setAltNames("ペロロニンギョウ Peroro Ningyou");
        setDescription("jp",
                "@C：対戦相手のシグニが正面にアタックする場合、代わりにこのシグニのあるシグニゾーンにアタックする。\n" +
                "@C：アップ状態のこのシグニがバトルか対戦相手の効果によって場を離れる場合、代わりにこのシグニをダウンしてもよい。\n" +
                "@U：対戦相手のターン終了時、このシグニをゲームから除外する。"
        );
        
        setName("en", "Peroro plush");
        setDescription("en",
                "@C: If a SIGNI on your opponent's field attacks in front of it, instead it attacks the SIGNI Zone that has this SIGNI.\n@C: If this upped SIGNI would leave the field through battle or by your opponent's effect, instead you may down this SIGNI.\n@U: At the end of your opponent's turn, remove this SIGNI from the game."
        );
        
        setName("en_fan", "Peroro Doll");
        setDescription("en_fan",
                "@C: If your opponent's SIGNI would attack to its front, it attacks the SIGNI zone where this SIGNI is placed instead.\n" +
                "@C: If this upped SIGNI would leave the field due to battle or opponent's effect, you may down it instead.\n" +
                "@U: At the end of your opponent's turn, exclude this SIGNI from the game."
        );
        
		setName("zh_simplified", "佩洛洛人偶");
        setDescription("zh_simplified", 
                "@C :对战对手的精灵对正面攻击的场合，作为替代，对这只精灵的精灵区攻击。\n" +
                "@C :竖直状态的这只精灵因为战斗或对战对手的效果离场的场合，作为替代，可以把这只精灵横置。\n" +
                "@U :对战对手的回合结束时，这只精灵从游戏除外。\n"
        );

        setCardFlags(CardFlag.CRAFT);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
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
            
            registerConstantAbility(new TargetFilter().OP().SIGNI(), new RuleCheckModifier<>(CardRuleCheckType.MUST_ATTACK_SIGNI_ZONE, this::onConstEff1ModRuleCheck));
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEff2ModOverrideCond,this::onConstEff2ModOverrideHandler))
            );
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private SIGNIZonePositionGroup onConstEff1ModRuleCheck(CardRuleCheckData data)
        {
            CardIndex cardIndex = data.getCardIndex();
            SIGNIZonePosition zonePosition = SIGNIZonePosition.getOppositeSIGNIPosition(SIGNIZonePosition.getSIGNIPositionByCardLocation(cardIndex.getLocation()));
            
            SIGNIZonePositionGroup group = new SIGNIZonePositionGroup(SIGNIZonePositionState.IGNORE);
            group.setState(zonePosition, SIGNIZonePositionState.DENY);
            group.setState(SIGNIZonePosition.getSIGNIPositionByCardLocation(getCardIndex().getLocation()), SIGNIZonePositionState.ALLOW);
            return group;
        }
        
        private boolean onConstEff2ModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return !isState(CardStateFlag.DOWNED) &&
                    event.getSourceCardIndex() != null && !isOwnCard(event.getSourceCardIndex()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEff2ModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionDown(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            exclude(getCardIndex());
        }
    }
}

