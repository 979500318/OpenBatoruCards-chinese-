package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B2_SouiDissonaAzureGeneral extends Card {

    public SIGNI_B2_SouiDissonaAzureGeneral()
    {
        setImageSets("WXDi-P12-073", "SPDi27-03");

        setOriginalName("蒼将　ソウイ//ディソナ");
        setAltNames("ソウショウソウイディソナ Soushou Soui Disona");
        setDescription("jp",
                "@C：対戦相手の凍結状態のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。\n" +
                "@E：対戦相手の手札が１枚以下の場合、対戦相手のシグニ１体を対象とし、それを凍結し、ターン終了時まで、このシグニのパワーを＋4000する。"
        );

        setName("en", "Soui//Dissona, Azure General");
        setDescription("en",
                "@C: If a frozen SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put into the trash instead of the Ener Zone.\n" +
                "@E: If your opponent has one or fewer cards in their hand, freeze target SIGNI on your opponent's field and this SIGNI gets +4000 power until end of turn."
        );
        
        setName("en_fan", "Soui//Dissona, Azure General");
        setDescription("en_fan",
                "@C: If this SIGNI would banish your opponent's frozen SIGNI in battle, it is put into the trash instead of the ener zone.\n" +
                "@E: If there is 1 or less cards in your opponent's hand, target 1 of your opponent's SIGNI, and until end of turn, freeze it, and until end of turn, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "苍将 索薇//失调");
        setDescription("zh_simplified", 
                "@C :对战对手的冻结状态的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n" +
                "@E :对战对手的手牌在1张以下的场合，对战对手的精灵1只作为对象，将其冻结，直到回合结束时为止，这只精灵的力量+4000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler)
                )
            );
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && getEvent().getCaller().isState(CardStateFlag.FROZEN);
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onEnterEff()
        {
            if(getHandCount(getOpponent()) <= 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                
                freeze(target);
                
                gainPower(getCardIndex(), 4000, ChronoDuration.turnEnd());
            }
        }
    }
}
