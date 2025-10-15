package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W1_CodeArtLIONDissona extends Card {

    public SIGNI_W1_CodeArtLIONDissona()
    {
        setImageSets("WXDi-P13-056");

        setOriginalName("コードアート　LION//ディソナ");
        setAltNames("コードアートリオンディソナ Koodo Aato Rion Disona");
        setDescription("jp",
                "@C：あなたのターンの間、あなたのトラッシュにスペルが３枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：あなたがスペルを使用したとき、ターン終了時まで、このシグニは@>@C：対戦相手のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。@@を得る。"
        );

        setName("en", "LION//Dissona, Code: Art");
        setDescription("en",
                "@C: During your turn, as long as there are three or more spells in your trash, this SIGNI gets +5000 power.\n@U: Whenever you use a spell, this SIGNI gains@>@C: If a SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put into the trash instead of the Ener Zone.@@until end of turn."
        );
        
        setName("en_fan", "Code Art LION//Dissona");
        setDescription("en_fan",
                "@C: During your turn, as long as there are 3 or more spells in your trash, this SIGNI gets +5000 power.\n" +
                "@U: When you use a spell, until end of turn, this SIGNI gains:" +
                "@>@C: If this SIGNI would banish your opponent's SIGNI in battle, it is put into the trash instead of the ener zone."
        );

		setName("zh_simplified", "必杀代号 LION//失调");
        setDescription("zh_simplified", 
                "@C :你的回合期间，你的废弃区的魔法在3张以上时，这只精灵的力量+5000。\n" +
                "@U :当你把魔法使用时，直到回合结束时为止，这只精灵得到\n" +
                "@>@C :对战对手的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。@@\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));

            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return isOwnTurn() && new TargetFilter().own().spell().fromTrash().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond,this::onAttachedConstEffModOverrideHandler)
            ));
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && !isOwnCard(event.getCallerCardIndex());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}

