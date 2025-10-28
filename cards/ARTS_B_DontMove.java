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

public final class ARTS_B_DontMove extends Card {

    public ARTS_B_DontMove()
    {
        setImageSets("WD03-007","WD23-016-EA", "WXK01-018", "SP06-006","SP11-005");

        setOriginalName("ドント・ムーブ");
        setAltNames("ドントムーブ Donto Muubu Dont");
        setDescription("jp",
                "対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "Don't Move");
        setDescription("en",
                "Target up to 2 of your opponent's SIGNI, and down them."
        );

        setName("zh_simplified", "不能·行动");
        setDescription("zh_simplified", 
                "对战对手的精灵2只最多作为对象，将这些横置。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 3));
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}

