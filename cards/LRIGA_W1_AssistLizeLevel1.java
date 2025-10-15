package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_AssistLizeLevel1 extends Card {
    
    public LRIGA_W1_AssistLizeLevel1()
    {
        setImageSets("WXDi-D02-17AT");
        
        setOriginalName("【アシスト】リゼ　レベル１");
        setAltNames("アシストリゼレベルイチ Ashisuto Rize Reberu Ichi Assist Lize");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを７枚見る。その中から白の＜バーチャル＞のシグニを２枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );
        
        setName("en", "[Assist] Lize, Level 1");
        setDescription("en",
                "@E: Look at the top seven cards of your deck. Reveal up to two white <<Virtual>> SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "[Assist] Lize Level 1");
        setDescription("en_fan",
                "@E: Look at the top 7 cards of your deck. Reveal up to 2 white <<Virtual>> SIGNI from among them, and add them to your hand. Shuffle the rest and put them on the bottom of your deck."
        );
        
		setName("zh_simplified", "【支援】莉泽 等级1");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看7张牌。从中把白色的<<バーチャル>>精灵2张最多公开加入手牌，剩下的洗切放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LIZE);
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
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(7);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.WHITE).withClass(CardSIGNIClass.VIRTUAL).fromLooked());
            reveal(data);
            addToHand(data);
            
            int countLooked = getLookedCount();
            if(countLooked > 0)
            {
                forEachCardInLooked(cardIndexLooked -> {
                    returnToDeck(cardIndexLooked, DeckPosition.BOTTOM);
                });
                shuffleDeck(countLooked, DeckPosition.BOTTOM);
            }
        }
    }
}
