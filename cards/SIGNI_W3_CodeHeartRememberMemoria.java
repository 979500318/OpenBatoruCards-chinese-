package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityCostModifier;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_CodeHeartRememberMemoria extends Card {
    
    public SIGNI_W3_CodeHeartRememberMemoria()
    {
        setImageSets("WXDi-P06-031", "WXDi-P06-031P", "SPDi10-09");
        
        setOriginalName("コードハート　リメンバ//メモリア");
        setAltNames("コードハートリメンバメモリア Koodo Haato Rimenba Memoria");
        setDescription("jp",
                "@C：対戦相手のターンの間、対戦相手の、センタールリグとシグニの@A能力の使用コストは%X増える。\n" +
                "@C：このシグニがダウン状態であるかぎり、このシグニのパワーは＋3000され、対戦相手は追加で%Xを支払わないかぎり【ガード】ができない。\n" +
                "@A #D：あなたのライフクロスの一番上を見る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Remember//Memoria, Code: Heart");
        setDescription("en",
                "@C: During your opponent's turn, use costs of the @A abilities of your opponent's Center LRIG and SIGNI are increased by %X.\n" +
                "@C: As long as this SIGNI is downed, this SIGNI gets +3000 power and your opponent cannot [[Guard]] unless they pay an additional %X.\n" +
                "@A #D: Look at the top card of your Life Cloth." +
                "~#Choose one -- \n$$1 Return target upped SIGNI on your opponent's field to its owner's hand. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Code Heart Remember//Memoria");
        setDescription("en_fan",
                "@C: During your opponent's turn, the use costs of the @A abilities of your opponent's center LRIG and SIGNI are increased by %X.\n" +
                "@C: As long as this SIGNI is downed, this SIGNI gets +3000 power, and your opponent can't [[Guard]] unless they pay %X.\n" +
                "@A #D: Look at the top card of your life cloth." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "爱心代号  忆//回忆");
        setDescription("zh_simplified", 
                "@C $TP 对战对手的，核心分身和精灵的@A能力的使用费用增%X。\n" +
                "@C 这只精灵在横置状态时，这只精灵的力量+3000，对战对手如果不追加把%X:支付，那么不能[[防御]]。\n" +
                "@A 横置:看你的生命护甲最上面。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerConstantAbility(this::onConstEff1SharedCond, new TargetFilter().OP().or(new TargetFilter().anyLocation().SIGNI(), new TargetFilter().LRIG()),
                new AbilityCostModifier(this::onConstEff1SharedModGetSample1, new CostModifier(this::onConstEff1SharedModGetSample2, ModifierMode.INCREASE))
            );
            
            registerConstantAbility(this::onConstEff2Cond,
                new PowerModifier(3000),
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD,
                    TargetFilter.HINT_OWNER_OP, data -> new EnerCost(Cost.colorless(1))
                )
            );
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEff1SharedCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEff1SharedModGetSample1(Ability ability)
        {
            return ability instanceof ActionAbility;
        }
        private AbilityCost onConstEff1SharedModGetSample2()
        {
            return new EnerCost(Cost.colorless(1));
        }
        
        private ConditionState onConstEff2Cond()
        {
            return isState(CardStateFlag.DOWNED) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = look(CardLocation.LIFE_CLOTH);
            addToLifeCloth(cardIndex);
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
