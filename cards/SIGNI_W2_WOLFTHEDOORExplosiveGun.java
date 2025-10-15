package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardUnderType;
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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_W2_WOLFTHEDOORExplosiveGun extends Card {

    public SIGNI_W2_WOLFTHEDOORExplosiveGun()
    {
        setImageSets("WXDi-P15-078");

        setOriginalName("爆砲　WOLF//THE DOOR");
        setAltNames("バクホウウルフザドアー Bakuhou Urufu Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのアタックフェイズ開始時、【エナチャージ１】をする。@@を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に【ゲート】がある場合、対戦相手のシグニ１体を対象とし、このターン、それがバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。"
        );

        setName("en", "WOLF//THE DOOR, Erupting Cannon");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the beginning of your attack phase, [[Ener Charge 1]].@@@U: At the beginning of your attack phase, if there is a [[Gate]] on your field, if target SIGNI on your opponent's field is vanished through battle this turn, it is put into the trash instead of the Ener Zone."
        );
        
        setName("en_fan", "WOLF//THE DOOR, Explosive Gun");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the beginning of your attack phase, [[Ener Charge 1]].@@" +
                "@U: At the beginning of your attack phase, if there is a [[Gate]] on your field, target 1 of your opponent's SIGNI, and this turn, if it would be banished in battle, it is put into the trash instead of the ener zone."
        );

		setName("zh_simplified", "爆炮 WOLF//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的攻击阶段开始时，[[能量填充1]]。@@\n" +
                "@U :你的攻击阶段开始时，你的场上有[[大门]]的场合，对战对手的精灵1只作为对象，这个回合，其因为战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAutoEffCond);
            return attachedAuto;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();

                if(target != null)
                {
                    ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                    ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(target), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                        new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
                    ));
                    GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/trash", 0.75,3)));
                    attachPlayerAbility(getOwner(), attachedConst, record);
                }
            }
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && event.getSourceCardIndex() != null;
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}

