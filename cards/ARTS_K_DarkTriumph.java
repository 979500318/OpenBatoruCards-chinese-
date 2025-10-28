package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_K_DarkTriumph extends Card {

    public ARTS_K_DarkTriumph()
    {
        setImageSets("WX25-P1-TK4");

        setOriginalName("闇気揚々");
        setAltNames("ダークエール Daaku Eeru Dark Yell");
        setDescription("jp",
                "((クラフトであるアーツは、使用後にゲームから除外される))\n\n" +
                "次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋5000する。\n\n" +
                "@C：このアーツはあなたの、コストや効果でルリグデッキから他の領域に移動しない。"
        );

        setName("en", "Dark Triumph");
        setDescription("en",
                "((This craft is excluded from the game after use))\n\n" +
                "Until the end of your opponent's next turn, all of your SIGNI get +5000 power.\n\n" +
                "@C: This ARTS can't be moved from your LRIG deck to another zone by your costs or effects."
        );

        setName("zh_simplified", "暗气扬扬");
        setDescription("zh_simplified", 
                "直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+5000。\n" +
                "@C :这张必杀不会因为你的，费用或效果从分身牌组往其他的领域移动。"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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
            gainPower(getSIGNIOnField(getOwner()), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
