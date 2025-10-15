package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_K1_GuzukoUselessPrincessOfSecrecy extends Card {
    
    public LRIG_K1_GuzukoUselessPrincessOfSecrecy()
    {
        setImageSets("WXK01-031");
        
        setOriginalName("秘密の駄姫　グズ子");
        setAltNames("ヒミツノダキグズコ Himitsu no Daki Guzuko");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：カードを１枚引く。"
        );
        
        setName("en", "Guzuko, Useless Princess of Secrecy");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Draw 1 card."
        );
        
		setName("zh_simplified", "秘密的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:抽1张牌。\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
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
