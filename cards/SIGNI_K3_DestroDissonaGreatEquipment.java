package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
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
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_K3_DestroDissonaGreatEquipment extends Card {

    public SIGNI_K3_DestroDissonaGreatEquipment()
    {
        setImageSets("WXDi-P12-054");
        setLinkedImageSets("WXDi-P12-010");

        setOriginalName("大装　デストロ//ディソナ");
        setAltNames("タイソウデストロディソナ Taisou Desutoro Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にある黒のシグニ１体につき－1000する。\n" +
                "@E：あなたの場に《残黒の巫女　タマヨリヒメ》がいる場合、対戦相手のシグニ１体を対象とし、このターン、それがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Destro//Dissona, Full Armed");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --1000 power for each black SIGNI on your field until end of turn.\n@E: If \"Tamayorihime, Enduring Darkness Miko\" is on your field, during this turn, if target SIGNI on your opponent's field is vanished, it is put into the trash instead of the Ener Zone." +
                "~#Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Destro//Dissona, Large Equipment");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each of your black SIGNI.\n" +
                "@E: If there is \"Tamayorihime, Black Remnant Miko\" on your field, target 1 of your opponent's SIGNI, and this turn, if it would be banished, it is put into the trash instead of the ener zone." +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "大装 毁灭//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的黑色的精灵的数量，每有1只就-1000。\n" +
                "@E :你的场上有《残黒の巫女　タマヨリヒメ》的场合，对战对手的精灵1只作为对象，这个回合，其被破坏的场合，放置到能量区，作为替代，放置到废弃区。" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA | CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();

            if(target != null)
            {
                gainPower(target, -1000 * new TargetFilter().own().SIGNI().withColor(CardColor.BLACK).getValidTargetsCount(), ChronoDuration.turnEnd());
            }
        }

        private void onEnterEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("残黒の巫女　タマヨリヒメ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();

                if(target != null)
                {
                    ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                    ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(target), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                        new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
                    ));
                    GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/trash", 0.75,3)));
                    attachPlayerAbility(getOwner(), attachedConst, record);
                }
            }
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}

