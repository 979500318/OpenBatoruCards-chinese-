package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_W_GetRemember extends Card {
    
    public SPELL_W_GetRemember()
    {
        setImageSets("WXDi-P06-050");
        setLinkedImageSets("WXDi-P06-031");
        
        setOriginalName("ゲット・リメンバ―");
        setAltNames("ゲットリメンバ― Getto Rimenbaa");
        setDescription("jp",
                "以下の２つを行う。\n" +
                "$$1あなたのデッキの上からカードを３枚見る。その中から好きな枚数のカードを好きな順番でデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。\n" +
                "$$2カードを１枚引く。あなたの場に《コードハート　リメンバ//メモリア》がある場合、代わりにカードを２枚引く。"
        );
        
        setName("en", "Get Remember");
        setDescription("en",
                "Perform the following.\n" +
                "$$1 Look at the top three cards of your deck. Put any number of cards from among them on top of your deck in any order. Put the rest on the bottom of your deck in any order.\n" +
                "$$2 Draw a card. if there is \"Remember//Memoria, Code: Heart\" on your field, instead draw two cards."
        );
        
        setName("en_fan", "Get Remember");
        setDescription("en_fan",
                "@[@|Do the following 2:|@]@\n" +
                "$$1 Look at the top 3 cards of your deck. Return any number of cards from among them to the top of your deck in any order, and put the rest on the bottom of your deck in any order.\n" +
                "$$2 Draw 1 card. If there is a \"Code Heart Remember//Memoria\" on your field, draw 2 cards instead."
        );
        
		setName("zh_simplified", "获得·记忆");
        setDescription("zh_simplified", 
                "进行以下的2种。\n" +
                "$$1 从你的牌组上面看3张牌。从中把任意张数的牌任意顺序返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n" +
                "$$2 抽1张牌。你的场上有《コードハート　リメンバ//メモリア》的场合，作为替代，抽2张牌。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
        }
        
        private void onSpellEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.TOP).own().fromLooked());
            returnToDeck(data, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            
            draw(new TargetFilter().ownedBy(getOwner()).SIGNI().withName("コードハート　リメンバ//メモリア").getValidTargetsCount() == 0 ? 1 : 2);
        }
    }
}
