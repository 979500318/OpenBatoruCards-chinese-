package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_K2_CodeMazeCachtice extends Card {

    public SIGNI_K2_CodeMazeCachtice()
    {
        setImageSets("WX24-P4-087");

        setOriginalName("コードメイズ　チェイテ");
        setAltNames("コードメイズチェイテ Koodo Meizu Cheite");
        setDescription("jp",
                "@U $T1：対戦相手のシグニ１体がこのシグニの正面に配置されたとき、このターン、そのシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。" +
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Code Maze Cachtice");
        setDescription("en",
                "@U $T1: When 1 of your opponent's SIGNI is placed in front of this SIGNI, if that SIGNI would be banished this turn, it is put into the trash instead of the ener zone." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "迷宫代号 恰赫季采城堡");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手的精灵1只在这只精灵的正面配置时，这个回合，那只精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PLACE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.getLocation() == CardLocation.getOppositeSIGNILocation(getCardIndex().getLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            ChronoRecord record = new ChronoRecord(caller, ChronoDuration.turnEnd());
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(caller),
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
                )
            );
            GFX.attachToAbility(attachedConst, new GFXCardTextureLayer(caller, new GFXTextureCardCanvas("border/trash", 0.75,3)));
            attachPlayerAbility(getOwner(), attachedConst, record);
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
