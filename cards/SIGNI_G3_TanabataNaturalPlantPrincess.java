package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckCanPayEner;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

import java.util.List;

public final class SIGNI_G3_TanabataNaturalPlantPrincess extends Card {
    
    public SIGNI_G3_TanabataNaturalPlantPrincess()
    {
        setImageSets("WXDi-P10-041", "SPDi02-21");
        
        setOriginalName("羅植姫　タナバタ");
        setAltNames("ラショクヒメタナバタ Rashokuhime Tanabata");
        setDescription("jp",
                "@C：あなたのアタックフェイズの間、あなたがエナコストを支払う際、このシグニの下にあるカードをエナゾーンにあるかのようにトラッシュに置いて支払える。この方法でエナコストは１ターンに３つまでしか支払えない。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの上からカードを２枚このシグニの下に置く。\n" +
                "@U：このシグニが場を離れたとき、このシグニの下にあったカード１枚を対象とし、それをトラッシュからエナゾーンに置く。"
        );
        
        setName("en", "Tanabata, Natural Plant Queen");
        setDescription("en",
                "@C: When paying Ener costs during your attack phase, the cards underneath this SIGNI can be paid as if they were in the Ener Zone and put into your trash. Only Ener costs up to three can be paid this way in one turn.\n" +
                "@U: At the beginning of your attack phase, put the top two cards of your deck under this SIGNI. \n" +
                "@U: When this SIGNI leaves the field, put target card that was underneath this SIGNI from your trash into your Ener Zone."
        );
        
        setName("en_fan", "Tanabata, Natural Plant Princess");
        setDescription("en_fan",
                "@C: During your attack phase, while paying an ener cost, you may put cards from under this SIGNI into your trash as if they were in your ener zone. You can only pay up to 3 ener for ener costs in a turn this way.\n" +
                "@U: At the beginning of your attack phase, put the top 2 cards of your deck under this SIGNI.\n" +
                "@U: When this SIGNI leaves the field, target 1 card from your trash that was under this SIGNI, and put it into the ener zone."
        );
        
		setName("zh_simplified", "罗植姬 七夕竹");
        setDescription("zh_simplified", 
                "@C :你的攻击阶段期间，你把能量费用支付时，这只精灵的下面的牌像在能量区那样放置到废弃区，支付。这个方法只能把能量费用1回合3点最多支付。\n" +
                "@U :你的攻击阶段开始时，从你的牌组上面把2张牌放置到这只精灵的下面。（表向放置）\n" +
                "@U :当这只精灵离场时，这只精灵的下面原有的1张牌作为对象，将其从废弃区放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond,
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.DATA_CARDS_ENER_SUBSTITUTE, TargetFilter.HINT_OWNER_OWN, this::onConstEffMod1RuleCheck),
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_PAY_ENER, TargetFilter.HINT_OWNER_OWN, this::onConstEffMod2RuleCheck)
            );
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) ? ConditionState.OK : ConditionState.BAD;
        }
        private DataTable<CardIndex> onConstEffMod1RuleCheck(RuleCheckData data)
        {
            return new TargetFilter().under(getCardIndex()).getExportedData();
        }
        private RuleCheckState onConstEffMod2RuleCheck(RuleCheckData data)
        {
            return RuleCheckCanPayEner.getDataPickedCards(data).stream().
                    filter(cardIndex -> cardIndex.getLocation() == getCardIndex().getLocation()).count() <= 3 ? RuleCheckState.OK : RuleCheckState.BLOCK;
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC, 2);
        }
        
        private DataTable<CardIndex> dataUnder;
        private ConditionState onAutoEff2Cond()
        {
            if(CardLocation.isSIGNI(EventMove.getDataMoveLocation())) return ConditionState.BAD;

            dataUnder = new TargetFilter().own().under(getCardIndex()).getExportedData();
            return ConditionState.OK;
        }
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().fromTrash().match(dataUnder)).get();
            putInEner(target);
        }
    }
}

