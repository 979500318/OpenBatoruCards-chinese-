package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionBanish;
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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_R1_RilMemoriaSmallEquipment extends Card {

    public SIGNI_R1_RilMemoriaSmallEquipment()
    {
        setImageSets("WXDi-P10-052", "WXDi-P10-052P");

        setOriginalName("小装　リル//メモリア");
        setAltNames("ショウソウリルメモリア Shousou Riru Memoria");
        setDescription("jp",
                "@E：あなたの他のシグニ１体を選ぶ。\n" +
                "@C：対戦相手のターンの間、このシグニの@E能力で選んだシグニがバニッシュされる場合、代わりにこのシグニをバニッシュしてもよい。\n" +
                "@A $T1 #C #C #C：対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Ril//Memoria, Lightly Armed");
        setDescription("en",
                "@E: Choose another SIGNI on your field.\n" +
                "@C: During your opponent's turn, if the SIGNI chosen with the @E ability of this SIGNI would be vanished, you may instead vanish this SIGNI.\n" +
                "@A $T1 #C #C #C: Vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Ril//Memoria, Small Equipment");
        setDescription("en_fan",
                "@E: Choose 1 of your other SIGNI.\n" +
                "@C: During your opponent's turn, if the SIGNI chosen by this SIGNI's @E ability would be banished, you may banish this SIGNI instead.\n" +
                "@A $T1 #C #C #C: Target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "小装 莉露//回忆");
        setDescription("zh_simplified", 
                "@E :选你的其他的精灵1只。\n" +
                "@C 对战对手的回合期间，这只精灵的@E能力选的精灵被破坏的场合，作为替代，可以把这只精灵破坏。\n" +
                "@A $T1 #C #C #C:对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(3000);

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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().except(cardId), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler)
            ));

            ActionAbility act = registerActionAbility(new CoinCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        int instanceId;
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().except(getCardIndex())).get();
            if(cardIndex != null) instanceId = cardIndex.getIndexedInstance().getInstanceId();
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getCaller().getInstanceId() == instanceId;
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionBanish(getCardIndex()));
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
