package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.ActionCallDelayedEffect;
import open.batoru.core.gameplay.actions.ActionFlip;
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
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B3_CodeAncientsPartheno extends Card {
    
    public SIGNI_B3_CodeAncientsPartheno()
    {
        setImageSets("WXDi-P00-038");
        
        setOriginalName("コードアンシエンツ　パルテノ");
        setAltNames("コードアンシエンツパルテノ Koodo Anshientsu Paruteno");
        setDescription("jp",
                "@U：対戦相手のターンの間、このシグニが場を離れる場合、代わりにこれを裏向きにしてもよい。そうした場合、次の次のあなたのメインフェイズ開始時、これと同じシグニゾーンにシグニがない場合、これを表向きにし、対戦相手の手札を２枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、%B %Bを支払ってもよい。そうした場合、それをデッキの一番下に置く。"
        );
        
        setName("en", "Parthenos, Code: Ancients");
        setDescription("en",
                "@U: During your opponent's turn, if this SIGNI would leave the field, you may instead turn it face-down. If you do, at the beginning of your following main phase after your next main phase, if there is no SIGNI in the same place as this card, turn this card face-up and your opponent discards two cards." +
                "~#You may pay %B %B. If you do, put target SIGNI on your opponent's field on the bottom of your opponent's deck."
        );
        
        setName("en_fan", "Code Ancients Partheno");
        setDescription("en_fan",
                "@U: During your opponent's turn, whenever this SIGNI would leave the field, you may turn it face down instead. If you do, at the beginning of your next main phase after your next main phase, if there are no SIGNI in the same SIGNI zone as this, turn this card face up, and your opponent discards 2 cards from their hand." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %B %B. If you do, put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "古神代号 帕特农");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵离场的场合，作为替代，可以将此牌变为里向。这样做的场合，下一个的下一个你的主要阶段开始时，与此牌相同精灵区没有精灵的场合，将此牌变为表向，对战对手把手牌2张舍弃。" +
                "~#对战对手的精灵1只作为对象，可以支付%B %B。这样做的场合，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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
            
            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER,OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionFlip(list.getSourceEvent().getCallerCardIndex(), CardFace.BACK));
            
            list.addAction(new ActionCallDelayedEffect(list.getSourceEvent().getCallerCardIndex(), sourceAbilityRC, new ChronoRecord(ChronoDuration.nextPhase(getOwner(), GamePhase.MAIN).repeat(2)), () -> {
                if(flip(getCardIndex(), CardFace.FRONT))
                {
                    discard(getOpponent(), 2);
                }
            }));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLUE, 2)))
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
