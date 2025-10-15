package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_AtCharge extends Card {
    
    public LRIGA_G1_AtCharge()
    {
        setImageSets("WXDi-P00-019");
        
        setOriginalName("アト＝チャージ");
        setAltNames("アトチャージ Ato Chaaji");
        setDescription("jp",
                "@E：[[エナチャージ２]]"
        );
        
        setName("en", "At =Charge=");
        setDescription("en",
                "@E: [[Ener Charge 2]]"
        );
        
        setName("en_fan", "At-Charge");
        setDescription("en_fan",
                "@E: [[Ener Charge 2]]"
        );
        
		setName("zh_simplified", "亚特=填充");
        setDescription("zh_simplified", 
                "@E :[[能量填充2]]（从你的牌组上面把2张牌放置到能量区）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
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
            enerCharge(2);
        }
    }
}
