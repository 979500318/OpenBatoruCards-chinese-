package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_K_CatchInNeverland extends Card {

    public ARTS_K_CatchInNeverland()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-007", Mask.VERTICAL+"WX25-CP1-007U");

        setOriginalName("ネバーランドでつかまえて");
        setAltNames("ネバーランドデツカマエテ Nebaarando de Tsukamaete");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚トラッシュに置いてもよい。その後、あなたのトラッシュから＜ブルアカ＞のシグニを３枚まで対象とし、それらを手札に加える。\n" +
                "&E４枚以上@0追加で０～１０の数字１つを宣言する。対戦相手のデッキの上からカードを宣言した数字に等しい枚数トラッシュに置く。"
        );

        setName("en", "Catch in Neverland");
        setDescription("en",
                "You may put the top 3 cards of your deck into the trash. Then, target up to 3 <<Blue Archive>> SIGNI from your trash, and add them to your hand.\n" +
                "&E4 or more@0 Declare a number between 0-10. Put a number of cards from the top of your opponent's deck into the trash equal to the declared number."
        );

		setName("zh_simplified", "在梦幻岛玩捉迷藏");
        setDescription("zh_simplified", 
                "可以从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把<<ブルアカ>>精灵3张最多作为对象，将这些加入手牌。\n" +
                "&E4张以上@0追加宣言0~10的数字1种。从对战对手的牌组上面把宣言数字相等张数的牌放置到废弃区。\n"
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
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash());
            addToHand(data);
            
            if(getAbility().isRecollectFulfilled())
            {
                int number = playerChoiceNumber(0,1,2,3,4,5,6,7,8,9,10) - 1;
                
                millDeck(getOpponent(), number);
            }
        }
    }
}
