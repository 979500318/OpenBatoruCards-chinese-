package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_AssistAngeLevel1Love extends Card {

    public LRIGA_W1_AssistAngeLevel1Love()
    {
        setImageSets("WXDi-CP01-014");

        setOriginalName("【アシスト】アンジュ　レベル１【愛】");
        setAltNames("アシストアンジュレベルイチアイ Ashisuto Anju Reberu Ichi Ai Assist Ange");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを７枚見る。その中から白と黒のシグニをそれぞれ１枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "[Assist] Ange, Level 1 [Love]");
        setDescription("en",
                "@E: Look at the top seven cards of your deck. Reveal up to one white SIGNI and one black SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "[Assist] Ange Level 1 [Love]");
        setDescription("en_fan",
                "@E: Look at the top 7 cards of your deck. Reveal up to 1 white and up to 1 black SIGNI from among them, and add them to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "【支援】安洁 等级1【爱】");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看7张牌。从中把白色和黑色的精灵各1张最多公开加入手牌，剩下的洗切放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final CardColor[] COLORS = {CardColor.WHITE, CardColor.BLACK};
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(7);

            DataTable<CardIndex> data = new DataTable<>();
            for(CardColor color : COLORS)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().except(data).withColor(color).fromLooked()).get();
                if(cardIndex != null) data.add(cardIndex);
            }
            reveal(data);
            addToHand(data);
            
            int countLooked = getLookedCount();
            if(countLooked > 0)
            {
                forEachCardInLooked(cardIndexLooked -> returnToDeck(cardIndexLooked, DeckPosition.BOTTOM));
                shuffleDeck(countLooked, DeckPosition.BOTTOM);
            }
        }
    }
}
