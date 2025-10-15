package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionReturnToDeck;
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

public final class SIGNI_B1_CodeMazeOokasoDissona extends Card {

    public SIGNI_B1_CodeMazeOokasoDissona()
    {
        setImageSets("WXDi-P13-071");

        setOriginalName("コードメイズ　オオカソ//ディソナ");
        setAltNames("コードメイズオオカソディソナ Koodo Meizu Ookaso Disona");
        setDescription("jp",
                "@C：対戦相手の凍結状態のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにデッキの一番下に置かれる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Ohkaso//Dissona, Code: Maze");
        setDescription("en",
                "@C: If a frozen SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put on the bottom of its owner's deck instead of the Ener Zone." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Code Maze Ookaso//Dissona");
        setDescription("en_fan",
                "@C: If this SIGNI would banish your opponent's frozen SIGNI in battle, it is put on the bottom of their deck instead of the ener zone." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "迷宫代号 往还//失调");
        setDescription("zh_simplified", 
                "@C :对战对手的冻结状态的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到牌组最下面。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler)
            ));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && !isOwnCard(event.getCallerCardIndex()) && event.getCallerCardIndex().getIndexedInstance().isState(CardStateFlag.FROZEN);
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionReturnToDeck(list.getSourceEvent().getCallerCardIndex(), DeckPosition.BOTTOM));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
