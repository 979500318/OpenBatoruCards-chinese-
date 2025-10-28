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

public final class ARTS_K_DarkOut extends Card {

    public ARTS_K_DarkOut()
    {
        setImageSets("WX25-P1-TK5");

        setOriginalName("ダーク・アウト");
        setAltNames("ダークアウト Daaku Auto");
        setDescription("jp",
                "((クラフトであるアーツは、使用後にゲームから除外される))\n\n" +
                "あなたのトラッシュから黒のシグニを３枚まで対象とし、それらを手札に加える。\n\n" +
                "@C：このアーツはあなたの、コストや効果でルリグデッキから他の領域に移動しない。"
        );

        setName("en", "Dark Out");
        setDescription("en",
                "((This craft is excluded from the game after use))\n\n" +
                "Target up to 3 black SIGNI from your trash, and add them to your hand.\n\n" +
                "@C: This ARTS can't be moved from your LRIG deck to another zone by your costs or effects."
        );

        setName("zh_simplified", "黑暗·放逐");
        setDescription("zh_simplified", 
                "从你的废弃区把黑色的精灵3张最多作为对象，将这些加入手牌。\n" +
                "@C :这张必杀不会因为你的，费用或效果从分身牌组往其他的领域移动。"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2));
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
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash());
            addToHand(data);
        }

        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
