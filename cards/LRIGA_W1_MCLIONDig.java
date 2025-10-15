package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_MCLIONDig extends Card {
    
    public LRIGA_W1_MCLIONDig()
    {
        setImageSets("WXDi-P02-017");
        
        setOriginalName("MC.LION-DIG");
        setAltNames("エムシーリオンディグ Emu Shii Rion Digu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からあなたのセンタールリグと共通する色を持つシグニと、センタールリグではないあなたのいずれかのルリグと共通する色を持つシグニを、それぞれ１枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "MC LION - DIG");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Reveal up to one SIGNI that shares a color with your center LRIG and up to one SIGNI that shares a color with a LRIG that is not your center LRIG from among those cards. Add the revealed cards to your hand and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "MC.LION - DIG");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Reveal up to 1 SIGNI that shares a common color with your center LRIG and up to 1 SIGNI that shares a common color with any of your LRIGs other than the center LRIG from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "MC.LION-DIG");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把持有与你的核心分身共通颜色的精灵和，持有与不是核心分身的你的任一只的分身共通颜色的精灵，各1张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
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
            look(5);
            
            DataTable<CardIndex> data = new DataTable<>();
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromLooked()).get();
            if(cardIndex != null) data.add(cardIndex);
            
            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().except(data).withColor(getLRIGAssistLeft(getOwner()).getIndexedInstance().getColor(),getLRIGAssistRight(getOwner()).getIndexedInstance().getColor()).fromLooked()).get();
            if(cardIndex != null) data.add(cardIndex);
            
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
