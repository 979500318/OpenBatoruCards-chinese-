package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;

public final class PIECE_R_BraveryHeroines extends Card {

    public PIECE_R_BraveryHeroines()
    {
        setImageSets("WXDi-P09-001");

        setOriginalName("ブレイブリー・ヒロインズ");
        setAltNames("ブレイブリーヒロインズ Bureiburii Heroinzu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "このターン、あなたが次にルリグをグロウする場合、それのグロウするためのコストは%X %X %X %X減る。"
        );

        setName("en", "Bravery Heroines");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "The next time you would grow an LRIG on your field this turn, the cost to grow that LRIG is reduced by %X %X %X %X."
        );
        
        setName("en_fan", "Bravery Heroines");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "The next time you grow a LRIG this turn, reduce its cost to grow by %X %X %X %X."
        );

		setName("zh_simplified", "巾帼·英雄");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "这个回合，你下一次把分身成长的场合，成长的费用减%X %X %X %X。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.RED);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        long cacheCountGrows;
        private void onPieceEff()
        {
            cacheCountGrows = getTurnGrowCount();
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(
                new TargetFilter().own().anyLRIG().fromLocation(CardLocation.DECK_LRIG),
                new CostModifier(this::onAttachedConstEffModGetSample, CostModifier.ModifierMode.REDUCE)
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return cacheCountGrows == getTurnGrowCount() ? ConditionState.OK : ConditionState.BAD;
        }
        private AbilityCost onAttachedConstEffModGetSample()
        {
            return new EnerCost(Cost.colorless(4));
        }
        private long getTurnGrowCount()
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameConst.GameEventId.GROW && isOwnCard(event.getCaller()));
        }
    }
}
