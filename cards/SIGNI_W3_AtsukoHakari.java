package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
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
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_W3_AtsukoHakari extends Card {

    public SIGNI_W3_AtsukoHakari()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-039");

        setOriginalName("秤アツコ");
        setAltNames("ハカリアツコ Hakari Atsuko");
        setDescription("jp",
                "@C：あなたのアップ状態の＜ブルアカ＞のシグニ１体が対戦相手の効果によって場を離れる場合、代わりに%Wを支払ってもよい。そうした場合、そのシグニをダウンする。\n" +
                "@U：あなたのターン終了時、あなたのすべての＜ブルアカ＞のシグニをアップする。" +
                "~{{C $TP：あなたのシグニのパワーを＋3000する。@@" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Hakari Atsuko");

        setName("en_fan", "Atsuko Hakari");
        setDescription("en",
                "@C: If 1 of your upped <<Blue Archive>> SIGNI would leave the field by your opponent's effect, you may pay %W instead. If you do, down that SIGNI.\n" +
                "@U: At the end of your turn, up all of your <<Blue Archive>> SIGNI." +
                "~{{C $TP: All of your SIGNI get +3000 power.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "秤亚津子");
        setDescription("zh_simplified", 
                "@C :你的竖直状态的<<ブルアカ>>精灵1只因为对战对手的效果离场的场合，作为替代，可以支付%W。这样做的场合，那只精灵#D。\n" +
                "@U :你的回合结束时，你的全部的<<ブルアカ>>精灵竖直。\n" +
                "~{{C$TP :你的精灵的力量+3000。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().own().SIGNI(), new PowerModifier(3000));
            cont.setCondition(this::onConstEffCond);
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSourceCardIndex()) &&
                   !event.getCaller().isState(CardStateFlag.DOWNED) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation()) &&
                   EnerCost.canPayCost(sourceAbilityRC, Cost.color(CardColor.WHITE, 1));
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addEnerPayAction(Cost.color(CardColor.WHITE, 1));
            list.addNonMandatoryAction(new ActionDown(list.getSourceEvent().getCallerCardIndex()));
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            up(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getExportedData());
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
