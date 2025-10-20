package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.actions.ActionDown;
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
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class RESONA_R3_CodeHeatWixongerRobo extends Card {

    public RESONA_R3_CodeHeatWixongerRobo()
    {
        setImageSets("WX25-P2-TK04");

        setOriginalName("コードヒート　ウイクロソジャーロボ");
        setAltNames("コードヒートウイクロソジャーロボ Koodo Hiito Uikurosojaarobo");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計３枚トラッシュに置く\n\n" +
                "@C：このシグニが対戦相手の効果によって場を離れる場合、代わりにこの能力を失う。そうした場合、このシグニをダウンする。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Heat Wixonger Robo");
        setDescription("en",
                "Put 3 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@C: If this SIGNI would leave the field by your opponent's effect, it loses this ability instead. If it does, down this SIGNI.\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

		setName("zh_simplified", "赤日代号 愿望交错机器人");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计3张放置到废弃区\n" +
                "@C :这只精灵因为对战对手的效果离场的场合，作为替代，这个能力失去。这样做的场合，这只精灵横置。\n" +
                "@U :当这只精灵攻击时，对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 3, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler)
            ));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSource()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            sourceAbilityRC.disable();
            list.addNonMandatoryAction(new ActionDown(getCardIndex()));
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}

