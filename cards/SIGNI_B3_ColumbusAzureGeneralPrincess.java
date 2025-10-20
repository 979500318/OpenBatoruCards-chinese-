package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_B3_ColumbusAzureGeneralPrincess extends Card {
    
    public SIGNI_B3_ColumbusAzureGeneralPrincess()
    {
        setImageSets("WXDi-P04-036");
        
        setOriginalName("蒼将姫　コロンブス");
        setAltNames("ソウショウキコロンブス Soushouki Koronbusu");
        setDescription("jp",
                "=H ルリグ２体\n\n" +
                "@C：あなたのルリグデッキにあるカードが１枚以下であるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニがアップ状態の場合、対戦相手のシグニ１体を対象とし、手札を３枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Columbus, Azure General Queen");
        setDescription("en",
                "=H Two LRIG \n" +
                "@C: As long as you have one or less cards in your LRIG Deck, this SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is upped, you may discard three cards. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Columbus, Azure General Princess");
        setDescription("en_fan",
                "=H 2 LRIGs\n\n" +
                "@C: As long as there are 1 or less cards in your LRIG deck, this SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is upped, target 1 of your opponent's SIGNI, and you may discard 3 cards from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "苍将姬 哥伦布");
        setDescription("zh_simplified", 
                "=H分身2只（当这只精灵出场时，如果不把你的竖直状态的分身2只横置，那么将此牌横置）\n" +
                "@C :你的分身牌组的牌在1张以下时，这只精灵的力量+3000。\n" +
                "@U :你的攻击阶段开始时，这只精灵在竖直状态的场合，对战对手的精灵1只作为对象，可以把手牌3张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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
            
            registerStockAbility(new StockAbilityHarmony(2, new TargetFilter()));
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getDeckCount(getOwner(), DeckType.LRIG) <= 1 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null && discard(0,3, ChoiceLogic.BOOLEAN).get() != null)
                {
                    banish(target);
                }
            }
        }
    }
}
