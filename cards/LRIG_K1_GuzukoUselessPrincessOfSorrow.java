package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIG_K1_GuzukoUselessPrincessOfSorrow extends Card {
    
    public LRIG_K1_GuzukoUselessPrincessOfSorrow()
    {
        setImageSets("WD22-010-G", "WDK04-004");
        
        setOriginalName("悲哀の駄姫　グズ子");
        setAltNames("ヒアイノダキグズコ Hiai no Daki Guzuko");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：#Cを得る。"
        );
        
        setName("en", "Guzuko, Useless Princess of Sorrow");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Gain #C."
        );
        
		setName("zh_simplified", "悲哀的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:得到#C。\n"
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
            gainCoins(1);
        }
    }
}
