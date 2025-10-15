package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;

public final class ARTS_R_EqualFieryFlags extends Card {

    public ARTS_R_EqualFieryFlags()
    {
        setImageSets("WDK01-008");

        setOriginalName("火旗相当");
        setAltNames("カキソウトウ Kaki Soutou");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1ターン終了時まで、対象のあなたのセンタールリグ１体は対象のあなたの＜乗機＞のシグニ１体に乗る。\n" +
                "$$2あなたのデッキの上からカードを５枚見る。その中から＜乗機＞のシグニを３枚まで公開し手札に加える。残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Equal Fiery Flags");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your <<Riding Machine>> SIGNI, and until end of turn, your center LRIG rides it.\n" +
                "$$2 Look at the top 5 cards of your deck. Reveal up to 3 <<Riding Machine>> SIGNI from among them, and add them to your hand. Shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "火旗相当");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 直到回合结束时为止，对象的你的核心分身1只在对象的你的<<乗機>>精灵1只搭乘。\n" +
                "$$2 从你的牌组上面看5张牌。从中把<<乗機>>精灵3张最多公开加入手牌。剩下的洗切放置到牌组最下面。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            arts.setModeChoice(1);
        }
        
        private void onARTSEff()
        {
            if(arts.getChosenModes() == 1)
            {
                CardIndex cardIndexLRIG = playerTargetCard(new TargetFilter(TargetHint.RIDE).own().LRIG()).get();
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.RIDE).own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE)).get();
                
                cardIndexLRIG.getIndexedInstance().ride(target);
            } else {
                look(5);
                
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE).fromLooked());
                reveal(data);
                addToHand(data);
                
                int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
                shuffleDeck(countReturned, DeckPosition.BOTTOM);
            }
        }
    }
}
