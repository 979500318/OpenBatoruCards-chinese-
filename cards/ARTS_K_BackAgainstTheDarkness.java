package open.batoru.data.cards;

import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_K_BackAgainstTheDarkness extends Card {

    public ARTS_K_BackAgainstTheDarkness()
    {
        setImageSets("WX25-P1-TK2");

        setOriginalName("背闇之陣");
        setAltNames("ハイアンジン Haianjin");
        setDescription("jp",
                "((クラフトであるアーツは、使用後にゲームから除外される))\n\n" +
                "手札を３枚捨てる。そうした場合、対戦相手のすべてのシグニをバニッシュする。\n\n" +
                "@C：このアーツはあなたの、コストや効果でルリグデッキから他の領域に移動しない。"
        );

        setName("en", "Back Against the Darkness");
        setDescription("en",
                "((This craft is excluded from the game after use))\n\n" +
                "Discard 3 cards from your hand. If you do, banish all of your opponent's SIGNI.\n\n" +
                "@C: This ARTS can't be moved from your LRIG deck to another zone by your costs or effects."
        );

        setName("zh_simplified", "背暗之阵");
        setDescription("zh_simplified", 
                "手牌3张舍弃。这样做的场合，对战对手的全部的精灵破坏。\n" +
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
            if(discard(3).size() == 3)
            {
                banish(getSIGNIOnField(getOpponent()));
            }
        }

        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
