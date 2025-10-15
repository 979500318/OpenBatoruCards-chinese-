package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class LRIGA_W1_RememberScrying extends Card {

    public LRIGA_W1_RememberScrying()
    {
        setImageSets("WXDi-P15-031");

        setOriginalName("リメンバ・スクライイング");
        setAltNames("リメンバスクライイング Rimenba Sukuraiingu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを４枚見る。その中からあなたのセンタールリグと共通する色を持つシグニと、あなたのセンタールリグと共通する色を持たないシグニをそれぞれ１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Remember Scrying");
        setDescription("en",
                "@E: Look at the top four cards of your deck. Reveal up to one SIGNI that shares a color with your Center LRIG and up to one SIGNI that does not share a color with your Center LRIG from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Remember Scrying");
        setDescription("en_fan",
                "@E: Look at the top 4 cards of your deck. Reveal up to 1 SIGNI that shares a common color with your center LRIG and up to 1 SIGNI that doesn't share a common color with your center LRIG from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "忆·占卜");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看4张牌。从中把持有与你的核心分身共通颜色的精灵和，不持有与你的核心分身共通颜色的精灵各1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REMEMBER);
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
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromLooked());
            data.add(playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withColor(getLRIG(getOwner()).getIndexedInstance().getColor())).except(data.get()).fromLooked()).get());
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
