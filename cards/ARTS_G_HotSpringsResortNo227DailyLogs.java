package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.DataTable;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class ARTS_G_HotSpringsResortNo227DailyLogs extends Card {

    public ARTS_G_HotSpringsResortNo227DailyLogs()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-029");

        setOriginalName("227号温泉郷の運営記録！");
        setAltNames("ニーニーナナゴウオンセンキョウノウンエイキロク Nii Nii Nanago Gouounsenkyounouneikiroku");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜ブルアカ＞のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。その後、この方法で緑の＜ブルアカ＞のカードを１枚以上手札に加えた場合、あなたの手札からレベル２以下の＜ブルアカ＞のシグニを１枚まで場に出す。ターン終了時まで、この方法で場に出たシグニのパワーを＋3000し、そのシグニは【ランサー】を得る。"
        );

        setName("en", "Hot Springs Resort No. 227 Daily Logs!");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 2 <<Blue Archive>> cards from among them, add them to your hand, and put the rest on the bottom of your deck in any order. Then, if you added 1 or more green <<Blue Archive>> cards to your hand this way, put up to 1 level 2 or lower <<Blue Archive>> SIGNI from your hand onto the field. Until end of turn, that SIGNI gets +3000 power, and it gains [[Lancer]]."
        );

		setName("zh_simplified", "227号温泉乡的运营记录！");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<ブルアカ>>牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。然后，这个方法把绿色的<<ブルアカ>>牌1张以上加入手牌的场合，从你的手牌把等级2以下的<<ブルアカ>>精灵1张最多出场。直到回合结束时为止，这个方法出场的精灵的力量+3000，那只精灵得到[[枪兵]]。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked());
            reveal(data);
            boolean match = data.get() != null && data.stream().anyMatch(cardIndex ->
                cardIndex.getIndexedInstance().getColor().matches(CardColor.GREEN) &&
                cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE)
            );
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(match)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).withLevel(0,2).fromHand().playable()).get();
                
                if(putOnField(cardIndex))
                {
                    gainPower(cardIndex, 3000, ChronoDuration.turnEnd());
                    attachAbility(cardIndex, new StockAbilityLancer(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
