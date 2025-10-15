package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_K_DarkBoundary extends Card {

    public ARTS_K_DarkBoundary()
    {
        setImageSets("WX25-P1-TK1");

        setOriginalName("ダーク・バウンダリー");
        setAltNames("ダークバウンダリー Daaku Baundarii");
        setDescription("jp",
                "((クラフトであるアーツは、使用後にゲームから除外される))\n\n" +
                "対戦相手のシグニを２体まで対象とし、それらを手札に戻す。\n\n" +
                "@C：このアーツはあなたの、コストや効果でルリグデッキから他の領域に移動しない。"
        );

        setName("en", "Dark Boundary");
        setDescription("en",
                "((This craft is excluded from the game after use))\n\n" +
                "Target up to 2 of your opponent's SIGNI, and return them to their hand.\n\n" +
                "@C: This ARTS can't be moved from your LRIG deck to another zone by your costs or effects."
        );

		setName("zh_simplified", "黑暗·界限");
        setDescription("zh_simplified", 
                "对战对手的精灵2只最多作为对象，将这些返回手牌。\n" +
                "@C :这张必杀不会因为你的，费用或效果从分身牌组往其他的领域移动。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(3));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_MOVED, this::onConstEffModRuleCheck));
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).OP().SIGNI());
            addToHand(data);
        }
        
        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
