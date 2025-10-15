package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
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
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_K2_HanarePoisonSlash extends Card {

    public LRIGA_K2_HanarePoisonSlash()
    {
        setImageSets("WX24-P4-042");

        setOriginalName("ハナレ//ポイズンスラッシュ");
        setAltNames("ハナレポイズンスラッシュ Hanare Poizun Surasshu");
        setDescription("jp",
                "@E：このターン、対戦相手のシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。\n" +
                "@E：ターン終了時まで、対戦相手のすべてのシグニのパワーを－5000する。"
        );

        setName("en", "Hanare//Poison Slash");
        setDescription("en",
                "@E: This turn, if your opponent's SIGNI would be banished, it is put into the trash instead of the ener zone.\n" +
                "@E: Until end of turn, all of your opponent's SIGNI get --5000 power."
        );

		setName("zh_simplified", "离//涂毒斩击");
        setDescription("zh_simplified", 
                "@E :这个回合，对战对手的精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。（这个能力的发动后出场的精灵，也给予这个效果的影响）\n" +
                "@E :直到回合结束时为止，对战对手的全部的精灵的力量-5000。（这个能力的发动后出场的精灵，不受这个效果的影响）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANARE);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
            ));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private void onEnterEff2()
        {
            gainPower(getSIGNIOnField(getOpponent()), -5000, ChronoDuration.turnEnd());
        }
    }
}
