package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class LRIGA_W1_HifumiAjitani extends Card {

    public LRIGA_W1_HifumiAjitani()
    {
        setImageSets("WXDi-CP02-025");

        setOriginalName("阿慈谷ヒフミ");
        setAltNames("アジタニヒフミ Ajitani Hifumi");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からシグニを１枚までと、そのシグニと共通するクラスを持ち無色ではないシグニを１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Ajitani Hifumi");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Reveal up to one SIGNI and one non-colorless SIGNI that shares a class with that SIGNI from among them, and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Hifumi Ajitani");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Reveal up to 1 SIGNI and up to 1 non-colorless SIGNI that shares a common class with it from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "阿慈谷日富美");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把精灵1张最多和，持有与那张精灵共通类别的不是无色的精灵1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
            if(data.get() != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked().except(data.get());
                if(data.get().getIndexedInstance() != null) filter = filter.withColor().withClass(data.get().getIndexedInstance().getSIGNIClass());
                data.add(playerTargetCard(0,1, filter).get());
            }
            reveal(data);
            addToHand(data);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}


