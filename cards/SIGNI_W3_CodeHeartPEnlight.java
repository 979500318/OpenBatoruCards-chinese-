package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_CodeHeartPEnlight extends Card {
    
    public SIGNI_W3_CodeHeartPEnlight()
    {
        setImageSets("WXDi-P03-036");
        
        setOriginalName("コードハート　Ｐンライト");
        setAltNames("コードハートピーンライト Koodo Haato Pii Nraito Penlight");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C：%X %Xを支払わないかぎりアタックできない。@@を得る。\n" +
                "@U：あなたのターン終了時、このターンにあなたがスペルを使用していた場合、次の対戦相手のターン終了時まで、このシグニは[[シャドウ（シグニ）]]を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "P-Nlight, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, target LRIG on your opponent's field gains@>@C: This LRIG cannot attack unless you pay %X %X.@@until end of turn.\n" +
                "@U: At the end of your turn, if you have used a spell this turn, this SIGNI gains [[Shadow -- SIGNI]] until the end of your opponent's next end phase." +
                "~#Choose one -- \n$$1 Draw a card. \n$$2 Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Code Heart P Enlight");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C: Can't attack unless you pay %X %X.@@" +
                "@U: At the end of your turn, if you used a spell this turn, until the end of your opponent's next turn, this SIGNI gains [[Shadow (SIGNI)]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "爱心代号 应援棒");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C 如果不把%X %X:支付，那么不能攻击。@@\n" +
                "@U :你的回合结束时，这个回合你把魔法使用过的场合，直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（精灵）]]。" +
                "~#以下选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> {
                    return new EnerCost(Cost.colorless(2));
                }));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                draw(1);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            }
        }
    }
}
