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
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.FieldZone;

public final class LRIG_B3_DigitalAyaIII extends Card {
    
    public LRIG_B3_DigitalAyaIII()
    {
        setImageSets("WXDi-P07-010", "WXDi-P07-010U");
        
        setOriginalName("でじたるあーや！Ⅲ");
        setAltNames("デジタルアーヤスリー Dejitaru Aaya Surii");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、対戦相手のデッキの一番上を公開する。それがレベル１のシグニの場合、$$1を行う。レベル２のシグニの場合、$$2を行う。レベル３のシグニの場合、$$1か$$2を行う。スペルの場合、$$1と$$2を行う。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2対戦相手は手札を１枚捨てる。\n" +
                "@A $G1 %B0：対戦相手のシグニ１体を対象とし、それを裏向きにする。各アタックフェイズ開始時、裏向きのそれと同じ場所にシグニがない場合、対戦相手は%X %Xを支払うか手札を２枚捨ててもよい。そうした場合、それを表向きにする。"
        );
        
        setName("en", "Digital Aya! III");
        setDescription("en",
                "@U: Whenever this LRIG attacks, reveal the top card of your opponent's deck. If it is a level one SIGNI, perform $$1. If it is a level two SIGNI, perform $$2. If it is a level three SIGNI, perform $$1 or $$2. If it is a spell, perform $$1 and $$2.\n" +
                "$$1 Draw a card.\n" +
                "$$2 Your opponent discards a card.\n" +
                "@A $G1 %B0: Turn target SIGNI on your opponent's field face down. At the beginning of each attack phase, if a SIGNI is not in the same position as the face-down SIGNI, your opponent may pay %X %X or discard two cards. If they do, turn that SIGNI face up."
        );
        
        setName("en_fan", "Digital Aya! III");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, reveal the top card of your opponent's deck. If it is a level 1 SIGNI, do $$1. If it is a level 2 SIGNI, do $$2. If it is a level 3 SIGNI, do $$1 or $$2. If it is a spell, do $$1 and $$2.\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Your opponent discards 1 card from their hand.\n" +
                "@A $G1 %B0: Target 1 of your opponent's SIGNI, and turn it face down. At the beginning of each attack phase, if there is no SIGNI in the same place, your opponent may pay %X %X or discard 2 cards from their hand. If they do, turn it face up."
        );
        
		setName("zh_simplified", "数码亚弥! III");
        setDescription("zh_simplified", 
                "@U 当这只分身攻击时，对战对手的牌组最上面公开。其是等级1的精灵的场合，进行$$1 。等级2的精灵的场合，进行$$2 。等级3的精灵的场合，进行$$1 或$$2 。魔法的场合，进行$$1 和$$2 。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 对战对手把手牌1张舍弃。\n" +
                "@A $G1 %B0对战对手的精灵1只作为对象，将其变为里向。各攻击阶段开始时，与里向的其相同场所没有精灵的场合，对战对手可以支付%X %X:或把手牌2张舍弃。这样做的场合，将其变为表向。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+1);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = reveal(getOpponent());
            
            if(cardIndex != null)
            {
                int level = cardIndex.getIndexedInstance().getLevel().getValue();
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                switch(level)
                {
                    case 1: onAutoEffAction1();break;
                    case 2: onAutoEffAction2();break;
                    case 3:
                    {
                        if(playerChoiceMode() == 1) onAutoEffAction1();
                        else onAutoEffAction2();
                        break;
                    }
                    default:
                    {
                        if(cardIndex.getCardReference().getType() == CardType.SPELL)
                        {
                            onAutoEffAction1();
                            onAutoEffAction2();
                        }
                        break;
                    }
                }
            }
        }
        private void onAutoEffAction1()
        {
            draw(1);
        }
        private void onAutoEffAction2()
        {
            discard(getOpponent(), 1);
        }
        
        private CardIndex target;
        private FieldZone fieldZone;
        private void onActionEff()
        {
            target = playerTargetCard(new TargetFilter(TargetHint.FLIP).OP().SIGNI()).get();
            
            if(target != null)
            {
                fieldZone = (FieldZone)target.getZoneByLocation();
                
                if(flip(target, CardFace.BACK))
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                    attachedAuto.setCondition(this::onAttachedAutoEffCond);
                    
                    attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
                }
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(target.getLocation() == fieldZone.getZoneLocation())
            {
                if(!FieldZone.isOccupied(fieldZone) && pay(getOpponent(), new EnerCost(Cost.colorless(2)), new DiscardCost(0,2, ChoiceLogic.BOOLEAN)))
                {
                    flip(target, CardFace.FRONT);
                    CardAbilities.removePlayerAbility(getAbility());
                }
            } else {
                CardAbilities.removePlayerAbility(getAbility());
            }
        }
    }
}
