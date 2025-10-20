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
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class ARTS_B_DontLeaveMe extends Card {

    public ARTS_B_DontLeaveMe()
    {
        setImageSets("WDK02-008", "PR-K045");

        setOriginalName("ドント・リブミー");
        setAltNames("ドントリブミー Donto Ribu Mii Dont");
        setDescription("jp",
                "@[ベット]@ -- #C #C\n\n" +
                "対戦相手のシグニ１体を対象とし、それをダウンする。あなたがベットしていた場合、代わりに対戦相手のシグニ２体を対象とし、それらをダウンする。"
        );

        setName("en", "Don't Leave Me");
        setDescription("en",
                "@[Bet]@ -- #C #C\n\n" +
                "Target 1 of your opponent's SIGNI, and down it. If you bet, instead, target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "不能·离开");
        setDescription("zh_simplified", 
                "下注—#C #C\n" +
                "对战对手的精灵1只作为对象，将其横置。你下注的场合，作为替代，对战对手的精灵2只作为对象，将这些横置。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
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
            arts.setBetCost(new CoinCost(2));
        }
        
        private void onARTSEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.DOWN).OP().SIGNI();
            DataTable<CardIndex> data;
            if(!arts.hasUsedBet())
            {
                data = playerTargetCard(filter);
            } else {
                data = playerTargetCard(0,2, filter);
            }
            down(data);
        }
    }
}
