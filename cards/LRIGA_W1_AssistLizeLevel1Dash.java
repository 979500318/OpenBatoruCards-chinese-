package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_AssistLizeLevel1Dash extends Card {
    
    public LRIGA_W1_AssistLizeLevel1Dash()
    {
        setImageSets("WXDi-P00-024");
        
        setOriginalName("【アシスト】リゼ　レベル１’");
        setAltNames("アシストリゼレベルイチダッシュ Ashisuto Rize Reberu Ichi Dasshu Dash Assist Lize");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを７枚見る。その中から黒の＜バーチャル＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "[Assist] Lize, Level 1'");
        setDescription("en",
                "@E: Look at the top seven cards of your deck. Reveal up to two black <<Virtual>> SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "[Assist] Lize Level 1'");
        setDescription("en_fan",
                "@E: Look at the top 7 cards of your deck. Reveal up to 2 black <<Virtual>> SIGNI from among them, and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "【支援】莉泽 等级1'");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看7张牌。从中把黑色的<<バーチャル>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
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
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).withClass(CardSIGNIClass.VIRTUAL).fromLooked());
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
