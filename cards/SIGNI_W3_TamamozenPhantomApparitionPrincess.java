package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_TamamozenPhantomApparitionPrincess extends Card {
    
    public SIGNI_W3_TamamozenPhantomApparitionPrincess()
    {
        setImageSets("WXDi-P05-033");
        
        setOriginalName("幻怪姫　タマモゼン");
        setAltNames("ゲンカイキタマモゼン Genkaiki Tamamozen");
        setDescription("jp",
                "=R あなたのシグニ１体の上に置く\n\n" +
                "@C：対戦相手のターンの間、このシグニは[[シャドウ]]を得る。\n" +
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：%Xを支払わないかぎりアタックできない。@@を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Tamamozen, Phantom Spirit Queen");
        setDescription("en",
                "=R Place on top of a SIGNI on your field. \n" +
                "@C: During your opponent's turn, this SIGNI gains [[Shadow]].\n" +
                "@U: At the beginning of your opponent's attack phase, target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack unless you pay %X.@@until end of turn." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Tamamozen, Phantom Apparition Princess");
        setDescription("en_fan",
                "=R Put on 1 of your SIGNI\n\n" +
                "@C: During your opponent's turn, this SIGNI gains [[Shadow]].\n" +
                "@U: At the beginning of your opponent's attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack unless you pay %X.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "幻怪姬 玉藻前");
        setDescription("zh_simplified", 
                "=R在你的精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@C $TP :这只精灵得到[[暗影]]。（这只精灵不会被对战对手作为对象）\n" +
                "@U :对战对手的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C 如果不把%X:支付，那么不能攻击。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter());
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> {
                    return new EnerCost(Cost.colorless(1));
                }));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
