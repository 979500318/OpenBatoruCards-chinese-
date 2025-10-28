package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_K_DarkAnalyze extends Card {

    public ARTS_K_DarkAnalyze()
    {
        setImageSets("WX25-P1-TK3");

        setOriginalName("ダーク・アナライズ");
        setAltNames("ダークアナライズ Daaku Anaraizu");
        setDescription("jp",
                "((クラフトであるアーツは、使用後にゲームから除外される))\n\n" +
                "数字１つを宣言する。対戦相手の手札を見て、宣言した数字と同じレベルを持つすべてのシグニを捨てさせる。\n\n" +
                "@C：このアーツはあなたの、コストや効果でルリグデッキから他の領域に移動しない。"
        );

        setName("en", "Dark Analyze");
        setDescription("en",
                "((This craft is excluded from the game after use))\n\n" +
                "Declare a number. Look at your opponent's hand, and discard all SIGNI with the same level as the declared number.\n\n" +
                "@C: This ARTS can't be moved from your LRIG deck to another zone by your costs or effects."
        );

        setName("zh_simplified", "黑暗·分析");
        setDescription("zh_simplified", 
                "数字1种宣言。看对战对手的手牌，持有与宣言数字相同等级的全部的精灵舍弃。\n" +
                "@C :这张必杀不会因为你的，费用或效果从分身牌组往其他的领域移动。"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1));
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
            int level = playerChoiceNumber(0,1,2,3,4,5) - 1;
            
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            discard(new TargetFilter().OP().SIGNI().fromRevealed().withLevel(level).getExportedData());
            
            addToHand(getCardsInRevealed(getOpponent()));
        }

        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
