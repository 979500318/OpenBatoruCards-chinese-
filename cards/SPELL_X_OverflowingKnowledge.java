package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public class SPELL_X_OverflowingKnowledge extends Card {

    public SPELL_X_OverflowingKnowledge()
    {
        setImageSets("WX03-036", "WXK01-070");

        setOriginalName("焚発する知識");
        setAltNames("フンパツスルチシキ Funpatsu suru Chishiki");
        setDescription("jp",
                "カードを３枚引く。"
        );

        setName("en", "Overflowing Knowledge");
        setDescription("en",
                "Draw 3 cards."
        );

		setName("zh_simplified", "焚发的知识");
        setDescription("zh_simplified", 
                "抽3张牌。\n"
        );

        setType(CardType.SPELL);
        setCost(Cost.colorless(3));

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
            draw(3);
        }
    }
}
