package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class ARTS_G_CircleOfTransmigration extends Card {
    
    public ARTS_G_CircleOfTransmigration()
    {
        setImageSets("WD20-007", "WDK03-008", "PR-458");
        
        setOriginalName("生生流転");
        setAltNames("リマインド Rimaindo Remind");
        setDescription("jp",
                "@[ベット]@ － #C #C\n\n" +
                "あなたがベッドする場合、このアーツを使用するためのコストは%G0になる。\n\n" +
                "あなたのデッキの一番上のカードをライフクロスに加える。"
        );
        
        setName("en", "Circle of Transmigration");
        setDescription("en",
                "@[Bet]@ -- #C #C\n\n" +
                "If you bet, the cost for using this ARTS becomes %G0.\n\n" +
                "Add the top card of your deck to life cloth."
        );

        setName("es", "Circulo de Transmigración");
        setDescription("es",
                "@[Apostar]@ -- #C #C\n\n" +
                "Si apostaste, el costo de este ARTS se vuelve %G0.\n\n" +
                "Añade el tope de tu mazo a tu Life Cloth."
        );

        setName("zh_simplified", "生生流转");
        setDescription("zh_simplified", 
                "下注—#C #C\n" +
                "你下注的场合，这张必杀的使用费用变为%G0。\n" +
                "你的牌组最上面的牌加入生命护甲。"
        );
        
        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2) + Cost.colorless(2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK | UseTiming.SPELLCUTIN);
        
        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(2), Cost.color(CardColor.GREEN, 0), ModifierMode.SET);
        }
        
        private void onARTSEff()
        {
            addToLifeCloth(1);
        }
    }
}
