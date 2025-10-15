package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R2_ProboscisMonkeyPhantomBeast extends Card {
    
    public SIGNI_R2_ProboscisMonkeyPhantomBeast()
    {
        setImageSets("WXDi-P03-060", "SPDi01-109");
        
        setOriginalName("幻獣　テングザル");
        setAltNames("ゲンジュウテングザル Genjuu Tenguzaru");
        setDescription("jp",
                "@C：このシグニはパワーが12000以上であるかぎり、@>@C：対戦相手の効果によってダウンしない。@@を得る。\n" +
                "@A %R %X %X：このシグニのパワーが15000以上の場合、ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "Tengu Zaru, Phantom Terra Beast");
        setDescription("en",
                "@C: As long as this SIGNI's power is 12000 or more, it gains@>@C: This SIGNI cannot be downed by your opponent's effects.@@" +
                "@A %R %X %X: If this SIGNI's power is 15000 or more, it gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Proboscis Monkey, Phantom Beast");
        setDescription("en_fan",
                "@C: As long as this SIGNI's power is 12000 or more, it gains:" +
                "@>@C: Can't be downed by your opponent's effects.@@" +
                "@A %R %X %X: If this SIGNI's power is 15000 or more, until end of turn, it gains [[Assassin]]."
        );
        
		setName("zh_simplified", "幻兽 长鼻猴");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量在12000以上时，得到\n" +
                "@>@C 不会因为对战对手的效果#D。@@\n" +
                "@A %R%X %X:这只精灵的力量在15000以上的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(2)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getPower().getValue() >= 12000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_DOWNED, this::onAttachedConstEffModRuleCheck));
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private ConditionState onActionEffCond()
        {
            return getPower().getValue() < 15000 ||
                   getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability -> ability.getSourceStockAbility() instanceof StockAbilityAssassin) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            if(getPower().getValue() >= 15000)
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}
