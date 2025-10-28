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

public final class ARTS_G_HighSpeedTakeoff extends Card {

    public ARTS_G_HighSpeedTakeoff()
    {
        setImageSets("WX25-P2-042");

        setOriginalName("高速滑走");
        setAltNames("スリルライド Suriru Raido Thrill Ride High Speed");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。あなたのエナゾーンからカードを３枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "High-Speed Takeoff");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and this turn, the next time it attacks, disable that attack. Target up to 3 cards from your ener zone, and add them to your hand."
        );

        setName("zh_simplified", "高速滑走");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。从你的能量区把牌3张最多作为对象，将这些加入手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            disableNextAttack(target);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
    }
}
