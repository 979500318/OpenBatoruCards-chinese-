package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_G1_MidorikoDancingCombatGirlTypeOne_Lostorage extends Card {
    
    public LRIG_G1_MidorikoDancingCombatGirlTypeOne_Lostorage()
    {
        setImageSets("WXK01-024");
        
        setOriginalName("一型舞闘娘　翠子");
        setAltNames("イチガタブトウキミドリコ Ichigata Butouki Midoriko");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：カードを１枚引く。"
        );
        
        setName("en", "Midoriko, Dancing Combat Girl Type One");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Draw 1 card."
        );
        
		setName("zh_simplified", "一型舞斗娘 翠子");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY);
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
            draw(1);
        }
    }
}
