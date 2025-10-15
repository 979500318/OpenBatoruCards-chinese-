package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_G_BreakingWithTradition extends Card {

    public ARTS_G_BreakingWithTradition()
    {
        setImageSets("WX24-D4-08", "SPDi37-10");

        setOriginalName("守破離");
        setAltNames("ノーウィンド Noo Uindo No Wind");
        setDescription("jp",
                "対戦相手のルリグとシグニを合計２体まで対象とし、このターン、それらがそれぞれ次にアタックしたとき、そのアタックを無効にする。"
        );

        setName("en", "Breaking with Tradition");
        setDescription("en",
                "Target up to 2 of your opponent's LRIG and/or SIGNI, and this turn, the next time each of them attack, disable that attack."
        );

		setName("zh_simplified", "守破离");
        setDescription("zh_simplified", 
                "对战对手的分身和精灵合计2只最多作为对象，这个回合，当这些下一次攻击时，那次攻击无效。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2));
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().fromField());
            if(data.get() != null) for(int i=0;i<data.size();i++) disableNextAttack(data.get(i));
        }
    }
}

