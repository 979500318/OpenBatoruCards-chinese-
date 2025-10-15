package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W2_RoundHolyGeneral extends Card {
    
    public SIGNI_W2_RoundHolyGeneral()
    {
        setImageSets("WXDi-P06-046");
        
        setOriginalName("聖将　ラウンド");
        setAltNames("セイショウラウンド Seishou Raundo");
        setDescription("jp",
                "@C：あなたのライフクロスが２枚以下であるかぎり、このシグニのパワーは＋4000される。\n" +
                "@C：あなたのライフクロスが０枚であるかぎり、このシグニは@>@C：対戦相手のターンの間、対戦相手の効果によってバニッシュされない。@@を得る。"
        );
        
        setName("en", "Round, Blessed General");
        setDescription("en",
                "@C: As long as you have two or less cards in your Life Cloth, this SIGNI gets +4000 power.\n" +
                "@C: As long as you have no cards in your Life Cloth, this SIGNI gains@>@C: During your opponent's turn, this SIGNI cannot be vanished by your opponent's effects.@@"
        );
        
        setName("en_fan", "Round, Holy General");
        setDescription("en_fan",
                "@C: As long as you have 2 or less life cloth, this SIGNI gets +4000 power.\n" +
                "@C: As long as you have no life cloth, this SIGNI gains:" +
                "@>@C: During your opponent's turn, this SIGNI can't be banished by your opponent's effects."
        );
        
		setName("zh_simplified", "圣将 圆");
        setDescription("zh_simplified", 
                "@C :你的生命护甲在2张以下时，这只精灵的力量+4000。\n" +
                "@C :你的生命护甲在0张时，这只精灵得到\n" +
                "@>@C :对战对手的回合期间，不会因为对战对手的效果破坏。@@\n" +
                "。（与精灵战斗或力量在0以下的场合，会被破坏）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(4000));
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
        }
        
        private ConditionState onConstEff1Cond()
        {
            return getLifeClothCount(getOwner()) <= 2 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onConstEff2Cond()
        {
            return getLifeClothCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(this::onAttachedConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onAttachedConstEffModRuleCheck));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
