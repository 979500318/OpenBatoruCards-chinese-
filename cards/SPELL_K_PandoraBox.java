package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_K_PandoraBox extends Card {

    public SPELL_K_PandoraBox()
    {
        setImageSets("WXK01-069");

        setOriginalName("パンドラ・ボックス");
        setAltNames("パンドラボックス Pandora Bokkusu");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中から１枚を手札に加え、１枚をトラッシュに置き、残りをデッキの一番上に戻す。"
        );

        setName("en", "Pandora Box");
        setDescription("en",
                "Look at the top 3 cards of your deck. Add 1 card from among them to to your hand, put 1 card from among them into the trash, and put the rest on the top of your deck in any order."
        );

		setName("zh_simplified", "潘多拉·盒子");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把1张加入手牌，1张放置到废弃区，剩下的返回牌组最上面。\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY);
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
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            trash(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
