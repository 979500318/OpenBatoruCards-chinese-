package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_W2_NovaSupernova extends Card {
    
    public LRIGA_W2_NovaSupernova()
    {
        setImageSets("WXDi-P01-023");
        
        setOriginalName("ノヴァ＝スーパーノヴァ");
        setAltNames("ノヴァスーパーノヴァ Nova Suupaanova");
        setDescription("jp",
                "@E：対戦相手のすべてのシグニを手札に戻す。"
        );
        
        setName("en", "Nova =Supernova=");
        setDescription("en",
                "@E: Return all SIGNI on your opponent's field to their owner's hand."
        );
        
        setName("en_fan", "Nova-Supernova");
        setDescription("en_fan",
                "@E: Return all of your opponent's SIGNI from the field to their hand."
        );
        
		setName("zh_simplified", "超=超新星");
        setDescription("zh_simplified", 
                "@E :对战对手的全部的精灵返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(4));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            addToHand(getSIGNIOnField(getOpponent()));
        }
    }
}
