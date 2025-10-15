package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_TawilMemoriaCrimsonDevil extends Card {
    
    public SIGNI_R2_TawilMemoriaCrimsonDevil()
    {
        setImageSets("WXDi-P08-058", "WXDi-P08-058P");
        
        setOriginalName("紅魔　タウィル//メモリア");
        setAltNames("コウマタウィルメモリア Kouma Tauiru Memoria");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、%Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。\n" +
                "@U：対戦相手のターン終了時、このシグニを場からデッキの一番下に置いてもよい。そうした場合、カードを１枚引く。"
        );
        
        setName("en", "Tawil//Memoria, Crimson Evil");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, target SIGNI on your opponent's field gains@>@U: Whenever this SIGNI attacks, vanish it unless you pay %X.@@until end of turn.\n" +
                "@U: At the end of your opponent's turn, you may put this SIGNI on your field on the bottom of its owner's deck. If you do, draw a card."
        );
        
        setName("en_fan", "Tawil//Memoria, Crimson Devil");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, banish this SIGNI unless you pay %X.@@" +
                "@U: At the end of your opponent's turn, you may put this SIGNI from your field on the bottom of your deck. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "红魔 塔维尔//回忆");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X:支付，那么这只精灵破坏。@@\n" +
                "@U :对战对手的回合结束时，可以把这只精灵从场上放置到牌组最下面。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            if(!getAbility().getSourceCardIndex().getIndexedInstance().payEner(Cost.colorless(1)))
            {
                getAbility().getSourceCardIndex().getIndexedInstance().banish(getAbility().getSourceCardIndex());
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && returnToDeck(getCardIndex(), DeckPosition.BOTTOM))
            {
                draw(1);
            }
        }
    }
}
