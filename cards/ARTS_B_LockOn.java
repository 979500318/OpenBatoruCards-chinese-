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

public final class ARTS_B_LockOn extends Card {

    public ARTS_B_LockOn()
    {
        setImageSets("WX24-P2-005", "WX24-P2-005U");

        setOriginalName("ロック・オン");
        setAltNames("ロックオン Rokku On");
        setDescription("jp",
                "カードを２枚引く。その後、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの手札１枚につき－2000する。\n" +
                "&E４枚以上@0追加で対戦相手の手札を２枚見ないで選び、捨てさせる。"
        );

        setName("en", "Lock-On");
        setDescription("en",
                "Draw 2 cards. Then, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power for each card in your hand.\n" +
                "&E4 or more@0 Additionally, choose 2 cards from your opponent's hand without looking, and discard them."
        );

        setName("zh_simplified", "继续·缚锁");
        setDescription("zh_simplified", 
                "抽2张牌。然后，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的手牌的数量，每有1张就-2000。\n" +
                "&E4张以上@0追加不看对战对手的手牌选2张，舍弃。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }

        private void onARTSEff()
        {
            draw(2);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000 * getHandCount(getOwner()), ChronoDuration.turnEnd());
            
            if(getAbility().isRecollectFulfilled())
            {
                DataTable<CardIndex> data = playerChoiceHand(2);
                discard(data);
            }
        }
    }
}
