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
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;

public final class ARTS_K_DarkMiasma extends Card {

    public ARTS_K_DarkMiasma()
    {
        setImageSets("WX24-P1-009", "WX24-P1-009U");

        setOriginalName("ダーク・マイアズマ");
        setAltNames("ダークマイアズマ Daaku Miazuma");
        setDescription("jp",
                "以下の３つから２つまで選ぶ。\n" +
                "$$1あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニを２枚まで対象とし、それらを手札に加える。\n" +
                "$$2対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－20000する。\n" +
                "$$3対戦相手のデッキの上からカードを１０枚トラッシュに置く。"
        );

        setName("en", "Dark Miasma");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Target up to 2 SIGNI that share a common color with your center LRIG from your trash, and add them to your hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and until end of turn, it gets --20000 power.\n" +
                "$$3 Put the top 10 cards of your opponent's deck into the trash."
        );

        setName("zh_simplified", "黑暗·瘴气");
        setDescription("zh_simplified", 
                "从以下的3种选2种最多。\n" +
                "$$1 从你的废弃区把持有与你的核心分身共通颜色的精灵2张最多作为对象，将这些加入手牌。\n" +
                "$$2 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-20000。\n" +
                "$$3 从对战对手的牌组上面把10张牌放置到废弃区。"
        );

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
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(0,2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash());
                addToHand(data);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -20000, ChronoDuration.turnEnd());
            }
            if((modes & 1<<2) != 0)
            {
                millDeck(getOpponent(), 10);
            }
        }
    }
}

