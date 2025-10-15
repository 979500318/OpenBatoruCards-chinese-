package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_BarbarianVerdantGeneral extends Card {

    public SIGNI_G1_BarbarianVerdantGeneral()
    {
        setImageSets("WXDi-P11-071", "SPDi01-101");

        setOriginalName("翠将　バーバリアン");
        setAltNames("スイショウバーバリアン Suishou Baabarian");
        setDescription("jp",
                "@C：このシグニは各ターンに一度しかアタックできない。\n" +
                "@E %G：ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニのパワーが3000以下であるかぎり、【ランサー】を得る。@@を得る。"
        );

        setName("en", "Barbarian, Jade General");
        setDescription("en",
                "@C: This SIGNI can only attack once each turn.\n" +
                "@E %G: This SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI has power 3000 or less, this SIGNI gains [[Lancer]].@@until end of turn."
        );
        
        setName("en_fan", "Barbarian, Verdant General");
        setDescription("en_fan",
                "@C: This SIGNI can only attack once per turn.\n" +
                "@E %G: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 3000 or less, this SIGNI gains [[Lancer]]."
        );

		setName("zh_simplified", "翠将 野蛮人");
        setDescription("zh_simplified", 
                "@C :这只精灵在各回合只能攻击一次。\n" +
                "@E %G:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在3000以下时，得到[[枪兵]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ATTACK, this::onConstEffModRuleCheck));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff);
        }

        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && event.getCallerCardIndex() == getCardIndex()) > 0 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getOppositeSIGNI() != null && getOppositeSIGNI().getIndexedInstance().getPower().getValue() <= 3000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
    }
}

