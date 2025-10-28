package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class ARTS_K_DarkTwinkle extends Card {

    public ARTS_K_DarkTwinkle()
    {
        setImageSets("WXK01-034");

        setOriginalName("ダーク・トゥインクル");
        setAltNames("ダークトゥインクル Daaku Tuinkuru");
        setDescription("jp",
                "@[ベット]@ -- #C\n\n" +
                "あなたがベットする場合、このアーツの使用コストは%Kになる。\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Dark Twinkle");
        setDescription("en",
                "@[Bet]@ -- #C\n\n" +
                "If you bet, the cost for using this ARTS becomes %K.\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --15000 power."
        );

        setName("zh_simplified", "黑暗·彩耀");
        setDescription("zh_simplified", 
                "下注—#C\n" +
                "你下注的场合，这张必杀的使用费用变为%K。\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-15000。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 3));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(1), Cost.color(CardColor.BLACK, 1), ModifierMode.SET);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
