package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class PIECE_X_Kaleidoscope extends Card {

    public PIECE_X_Kaleidoscope()
    {
        setImageSets("WX24-P2-043");

        setOriginalName("カレイドスコープ");
        setAltNames("Kareidosukoopu");
        setDescription("jp",
                "このターン、あなたのルリグが次にアシストルリグにグロウする場合、グロウするためのルリグタイプは無視され、グロウするためのコストは%X減る。"
        );

        setName("en", "Kaleidoscope");
        setDescription("en",
                "This turn, the next time 1 of your LRIG grows into an Assist LRIG, its LRIG type is ignored while growing and its grow cost is reduced by %X."
        );

		setName("zh_simplified", "万华镜");
        setDescription("zh_simplified", 
                "这个回合，你的分身下一次把支援分身成长的场合，无视成长的分身类别，成长的费用减%X。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerPieceAbility(this::onPieceEff);
        }

        long cacheAssistGrowsCount;
        private void onPieceEff()
        {
            cacheAssistGrowsCount = GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && isOwnCard(event.getCaller()) && event.getCaller().getLocation() != CardLocation.LRIG);
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().anyLRIG().except(CardType.LRIG).fromLocation(CardLocation.DECK_LRIG),
                new RuleCheckModifier<>(CardRuleCheckType.MUST_IGNORE_GROW_LRIG_TYPE, data -> RuleCheckState.OK),
                new CostModifier(this::onAttachedConstEffMod1GetSample, ModifierMode.REDUCE)
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return cacheAssistGrowsCount == GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && isOwnCard(event.getCaller()) && event.getCaller().getLocation() != CardLocation.LRIG) ?
                    ConditionState.OK : ConditionState.BAD;
        }
        private AbilityCost onAttachedConstEffMod1GetSample(CardIndex cardIndex)
        {
            return new EnerCost(Cost.colorless(1));
        }
    }
}

