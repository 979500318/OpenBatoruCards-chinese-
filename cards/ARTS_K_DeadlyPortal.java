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

public final class ARTS_K_DeadlyPortal extends Card {

    public ARTS_K_DeadlyPortal()
    {
        setImageSets("WX24-P2-009", "WX24-P2-009U");

        setOriginalName("デッドリー・ポータル");
        setAltNames("デッドリーポータル Deddorii Pootaru");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚トラッシュに置いてもよい。その後、対戦相手のシグニを好きな数対象とし、ターン終了時まで、それらのパワーを合計であなたのトラッシュにあるカード１枚につき－1000する。この効果では1000単位でしか数字を割り振れない。\n" +
                "&E４枚以上@0追加であなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Deadly Portal");
        setDescription("en",
                "You may put the top 3 cards of your deck into the trash. Then, target any number of your opponent's SIGNI, and until end of turn, they get a total of up to --1000 power for each card in your trash. This effect can only be distributed in increments of 1000.\n" +
                "&E4 or more@0 Additionally, target up to 2 SIGNI that share a common color with your center LRIG from your trash, and add them to your hand."
        );

		setName("zh_simplified", "致死·入口");
        setDescription("zh_simplified", 
                "可以从你的牌组上面把3张牌放置到废弃区。然后，对战对手的精灵任意数量作为对象，直到回合结束时为止，这些的力量的合计依据你的废弃区的牌的数量，每有1张就-1000。这个效果只能以1000为单位把数字分配。\n" +
                "&E4张以上@0追加从你的废弃区把持有与你的核心分身共通颜色的精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            if(playerChoiceActivate())
            {
                millDeck(3);
            }
            
            distributePower(-1000 * getTrashCount(getOwner()));
            
            if(getAbility().isRecollectFulfilled())
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash());
                addToHand(data);
            }
        }
    }
}
