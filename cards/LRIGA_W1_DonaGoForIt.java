package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_DonaGoForIt extends Card {

    public LRIGA_W1_DonaGoForIt()
    {
        setImageSets("WXDi-P09-027");

        setOriginalName("ドーナ『がんばれ！』");
        setAltNames("ドーナガンバレ Doona Ganbare");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを４枚見る。その中からセンタールリグと共通のする色を持つシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Dona \"Go For It!\"");
        setDescription("en",
                "@E: Look at the top four cards of your deck. Reveal up to two SIGNI that share a color with your Center LRIG among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Dona \"Go For It!\"");
        setDescription("en_fan",
                "@E: Look at the top 4 cards of your deck. Reveal up to 2 SIGNI that share a common color with your center LRIG from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "多娜『加油！』");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看4张牌。从中把持有与你的核心分身共通颜色的精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            look(4);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromLooked());
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
