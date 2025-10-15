package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_B_DontMoveOn extends Card {

    public ARTS_B_DontMoveOn()
    {
        setImageSets("WX24-D3-08", "SPDi37-09");

        setOriginalName("ドント・ムーブ・オン");
        setAltNames("ドントムーブオン Donto Muubu On");
        setDescription("jp",
                "対戦相手のルリグとシグニを合計２体まで対象とし、それらをダウンする。"
        );

        setName("en", "Don't Move On");
        setDescription("en",
                "Target up to 2 of your opponent's LRIG and/or SIGNI, and down them."
        );

		setName("zh_simplified", "不能·继续·行动");
        setDescription("zh_simplified", 
                "对战对手的分身和精灵合计2只最多作为对象，将这些#D。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(3));
        setUseTiming(UseTiming.ATTACK);

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
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().fromField());
            down(data);
        }
    }
}

