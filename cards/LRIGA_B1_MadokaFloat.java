package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B1_MadokaFloat extends Card {
    
    public LRIGA_B1_MadokaFloat()
    {
        setImageSets("WXDi-D06-006");
        
        setOriginalName("マドカ／／フロート");
        setAltNames("マドカフロート Madoka Furooto");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：カードを３枚引く。"
        );
        
        setName("en", "Madoka//Float");
        setDescription("en",
                "@E @[Discard a card]@: Draw three cards."
        );
        
        setName("en_fan", "Madoka//Float");
        setDescription("en_fan",
                "@E @[Discard 1 card from your hand]@: Draw 3 cards."
        );
        
		setName("zh_simplified", "円//随和");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:抽3张牌。\n" +
                "（@E能力的:的左侧是费用。你能选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
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
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            draw(3);
        }
    }
}
