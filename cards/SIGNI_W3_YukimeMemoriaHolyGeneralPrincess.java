package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardLocation;
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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_YukimeMemoriaHolyGeneralPrincess extends Card {
    
    public SIGNI_W3_YukimeMemoriaHolyGeneralPrincess()
    {
        setImageSets("WXDi-P07-039", "WXDi-P07-039P");
        
        setOriginalName("聖将姫　ゆきめ//メモリア");
        setAltNames("セイショウキユキメメモリアル Seishouki Yukime Memoria");
        setDescription("jp",
                "@C：アタックフェイズの間、このシグニの正面のシグニがバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。\n" +
                "@U：あなたのアタックフェイズ開始時、数字１つを宣言する。対戦相手のデッキの一番上を公開し、そのカードが宣言された数字と同じレベルのシグニの場合、カードを１枚引く。\n" +
                "@A #C：対戦相手はデッキの一番上と手札を公開する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Yukime//Memoria, Blessed Warlord");
        setDescription("en",
                "@C: If the SIGNI in front of this SIGNI is vanished during an attack phase, it is put into the trash instead of the Ener Zone.\n" +
                "@U: At the beginning of your attack phase, declare a number. Reveal the top card of your opponent's deck. If that card is a SIGNI with the same level as the declared number, draw a card.\n" +
                "@A #C: Your opponent reveals their hand and the top card of their deck." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Yukime//Memoria, Holy General Princess");
        setDescription("en_fan",
                "@C: During the attack phase, if the SIGNI in front of this SIGNI would be banished, it is put into the trash instead of the ener zone.\n" +
                "@U: At the beginning of your attack phase, declare 1 number. Reveal the top card of your opponent's deck, and if that card is a SIGNI with the same level as the declared number, draw 1 card.\n" +
                "@A #C: Look at your opponent's hand and the top card of their deck." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "圣将姬 雪芽//回忆");
        setDescription("zh_simplified", 
                "@C :攻击阶段期间，这只精灵的正面的精灵被破坏的场合，放置到能量区，作为替代，放置到废弃区。\n" +
                "@U :你的攻击阶段开始时，数字1种宣言。对战对手的牌组最上面公开，那张牌是与宣言数字相同等级的精灵的场合，抽1张牌。\n" +
                "@A #C:对战对手把牌组最上面和手牌公开。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER,OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffSharedModOverrideHandler)
            ));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new CoinCost(1), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onConstEffSharedModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int number = playerChoiceNumber(0,1,2,3,4,5) - 1;
            
            CardIndex cardIndex = reveal(getOpponent());
            
            if(cardIndex != null &&
               CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) &&
               cardIndex.getIndexedInstance().getLevelByRef() == number)
            {
                draw(1);
            }
            
            returnToDeck(cardIndex, DeckPosition.TOP);
        }
        
        private void onActionEff()
        {
            if(reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true) > 0)
            {
                addToHand(getCardsInRevealed(getOpponent()));
            }
            
            if(reveal(getOpponent()) != null)
            {
                returnToDeck(getCardsInRevealed(getOpponent()), DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
