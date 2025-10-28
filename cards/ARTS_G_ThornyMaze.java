package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_G_ThornyMaze extends Card {

    public ARTS_G_ThornyMaze()
    {
        setImageSets("WXK01_TK-01A", "WXK03_TK-01A");

        setOriginalName("棘々迷路");
        setAltNames("ソーンウイップ Soon Uippu Thorn Whip");
        setDescription("jp",
                "((クラフトであるアーツは、使用後にゲームから除外される))\n\n" +
                "シグニ１体を対象とし、ターン終了時まで、それのパワーを＋2000する。"
        );

        setName("en", "Thorny Maze");
        setDescription("en",
                "((This craft is excluded from the game after use))\n\n" +
                "Target 1 SIGNI, and until end of turn, it gets +2000 power."
        );

        setName("zh_simplified", "荆棘迷路");
        setDescription("zh_simplified", 
                "精灵1只作为对象，直到回合结束时为止，其的力量+2000。（这张牌放置到分身牌组和检查区以外的场合，作为替代，从游戏除外）"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()).get();
            gainPower(target, 2000, ChronoDuration.turnEnd());
        }
    }
}
