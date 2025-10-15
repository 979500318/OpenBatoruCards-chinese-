package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_K3_CodeLabyrinthMuzicaMemoria extends Card {

    public SIGNI_K3_CodeLabyrinthMuzicaMemoria()
    {
        setImageSets("WXDi-P10-044", "WXDi-P10-044P");

        setOriginalName("コードラビリンス　ムジカ//メモリア");
        setAltNames("コードラビリンスムジカメモリア Koodo Rabirinsu Mujika Memoria");
        setDescription("jp",
                "@C：アタックフェイズの間、このシグニの正面のシグニのパワーを－2000する。\n" +
                "@C：アタックフェイズの間、このシグニの正面のシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュから#Gを持たないシグニを２枚まで対象とし、それらをデッキに加えてもよい。そうした場合、デッキをシャッフルする。"
        );

        setName("en", "Muzica//Memoria, Code: Labyrinth");
        setDescription("en",
                "@C: During an attack phase, the SIGNI in front of this SIGNI gets --2000 power.\n" +
                "@C: During an attack phase, if the SIGNI in front of this SIGNI is vanished, it is put into the trash instead of the Ener Zone.\n" +
                "@U: Whenever this SIGNI attacks, you may add up to two target SIGNI without a #G from your trash to your deck. If you do, shuffle your deck."
        );
        
        setName("en_fan", "Code Labyrinth Muzica//Memoria");
        setDescription("en_fan",
                "@C: During the attack phase, the SIGNI in front of this SIGNI gets --2000 power.\n" +
                "@C: During the attack phase, if the SIGNI in front of this SIGNI would be banished, it is put into the trash instead of the ener zone.\n" +
                "@U: Whenever this SIGNI attacks, you may target up to 2 SIGNI without #G @[Guard]@ from your trash, and shuffle them into your deck."
        );

		setName("zh_simplified", "迷牢代号 穆希卡//回忆");
        setDescription("zh_simplified", 
                "@C :攻击阶段期间，这只精灵的正面的精灵的力量-2000。\n" +
                "@C :攻击阶段期间，这只精灵的正面的精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n" +
                "@U 当这只精灵攻击时，从你的废弃区把不持有#G的精灵2张最多作为对象，可以将这些加入牌组。这样做的场合，牌组洗切。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new PowerModifier(-2000));
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEff2ModOverrideHandler)
                )
            );

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && cardIndex == getOppositeSIGNI() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onConstEff2ModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private void onAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            if(returnToDeck(data, DeckPosition.TOP) > 0) shuffleDeck();
        }
    }
}
