package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_YukayukaPoon extends Card {
    
    public LRIGA_W1_YukayukaPoon()
    {
        setImageSets("WXDi-P05-021");
        
        setOriginalName("ゆかゆか☆ぽーん");
        setAltNames("ユカユカポーン Yukayuka Poon");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中から#Gを持たないカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Yukayuka☆Pon");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Reveal up to two cards without a #G from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Yukayuka☆Poon");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Reveal up to 2 cards without #G @[Guard]@ from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "由香香☆乓");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面看3张牌。从中把不持有#G的牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
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
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromLooked());
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
