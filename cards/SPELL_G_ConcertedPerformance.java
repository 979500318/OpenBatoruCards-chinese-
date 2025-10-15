package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_G_ConcertedPerformance extends Card {
    
    public SPELL_G_ConcertedPerformance()
    {
        setImageSets("WXDi-P02-080");
        
        setOriginalName("連奏");
        setAltNames("レンソウ Rensou");
        setDescription("jp",
                "[[エナチャージ３]]をする。"
        );
        
        setName("en", "Heterophony");
        setDescription("en",
                "[[Ener Charge 3]]"
        );
        
        setName("en_fan", "Concerted Performance");
        setDescription("en_fan",
                "[[Ener Charge 3]]"
        );
        
		setName("zh_simplified", "连奏");
        setDescription("zh_simplified", 
                "[[能量填充3]]。（从你的牌组上面把3张牌放置到能量区）\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            enerCharge(3);
        }
    }
}
