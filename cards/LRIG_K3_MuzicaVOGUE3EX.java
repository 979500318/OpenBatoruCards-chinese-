package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_MuzicaVOGUE3EX extends Card {
    
    public LRIG_K3_MuzicaVOGUE3EX()
    {
        setImageSets("WXDi-P05-010");
        
        setOriginalName("VOGUE3-EX ムジカ");
        setAltNames("ボーグスリーイーエックスムジカ Boogu Surii Ekkusu Mujika");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたの他のルリグがグロウしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。\n" +
                "@U $T1：対戦相手のターンの間、あなたの他のルリグがグロウしたとき、あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $G1 %X：-Aを持たずグロウコストが%X0ではないあなたのアシストルリグ１体を対象とし、それをルリグデッキに戻す。"
        );
        
        setName("en", "Muzica, Vogue 3 - EX");
        setDescription("en",
                "@U $T1: During your turn, when another LRIG on your field grows, target SIGNI on your opponent's field gets --5000 power until end of turn.\n" +
                "@U $T1: During your opponent's turn, when another LRIG on your field grows, add target black SIGNI from your trash to your hand.\n" +
                "@A $G1 %X: Return target Assist LRIG on your field without -A and whose Grow Cost isn't %X0 to your LRIG deck."
        );
        
        setName("en_fan", "Muzica, VOGUE 3-EX");
        setDescription("en_fan",
                "@U $T1: During your turn, whenever 1 of your other LRIGs grows, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.\n" +
                "@U $T1: During your opponent's turn, whenever 1 of your other LRIGs grows, target 1 black SIGNI from your trash, and add it to your hand.\n" +
                "@A $G1 %X: Target 1 of your assist LRIGs without -A or grow cost %X0, and return it to the LRIG deck."
        );
        
		setName("zh_simplified", "VOGUE3-EX 穆希卡");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当你的其他的分身成长时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "@U $T1 :对战对手的回合期间，当你的其他的分身成长时，从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n" +
                "@A $G1 %X不持有-A的成长费用不是%X0:的你的支援分身1只作为对象，将其返回分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.GROW, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.GROW, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) && caller != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().anyLRIG().except(getCardIndex()).not(new TargetFilter().withUseTiming(UseTiming.ATTACK)).withCost()).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
