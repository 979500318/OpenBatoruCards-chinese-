package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
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

public final class LRIG_B3_MadokaVOGUE3EX extends Card {
    
    public LRIG_B3_MadokaVOGUE3EX()
    {
        setImageSets("WXDi-P04-010");
        
        setOriginalName("VOGUE3-EX マドカ");
        setAltNames("ボーグスリーイーエックスマドカ Boogu Surii Ii Ekkusu Madoka");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、各プレイヤーは、カードを１枚引き手札を１枚捨てる。\n" +
                "@E：以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を２枚捨てる。\n" +
                "$$2対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "@A $G1 %B0：あなたの手札が対戦相手より３枚以上多い場合、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );
        
        setName("en", "Madoka, Vogue 3 - EX");
        setDescription("en",
                "@U: At the beginning of your attack phase, each player draws a card and discards a card.\n" +
                "@E: Choose one of the following.\n" +
                "$$1 Your opponent discards two cards.\n" +
                "$$2 Freeze target LRIG on your opponent's field.\n" +
                "@A $G1 %B0: If you have three or more cards in your hand than your opponent, put target SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Madoka, VOGUE 3-EX");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, each player draws 1 card and discards 1 card from their hand.\n" +
                "@E: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 2 cards from their hand.\n" +
                "$$2 Target 1 of your opponent's LRIGs, and freeze it.\n" +
                "@A $G1 %B0: If there are at least 3 more cards in your hand than in your opponent's, target 1 of your opponent's SIGNI, and put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "VOGUE 3-EX 円");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，各玩家，抽1张牌并把手牌1张舍弃。\n" +
                "@E :从以下的2种选1种。\n" +
                "$$1 对战对手把手牌2张舍弃。\n" +
                "$$2 对战对手的分身1只作为对象，将其冻结。\n" +
                "@A $G1 %B0:你的手牌比对战对手多3张以上的场合，对战对手的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
            discard(1);
            
            draw(getOpponent(), 1);
            discard(getOpponent(), 1);
        }
        
        private void onEnterEff()
        {
            if(playerChoiceMode() == 1)
            {
                discard(getOpponent(), 2);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
                freeze(target);
            }
        }
        
        private void onActionEff()
        {
            if((getHandCount(getOwner()) - getHandCount(getOpponent())) >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
