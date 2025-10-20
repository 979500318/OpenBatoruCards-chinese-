package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_B2_TamagoDrumroll extends Card {
    
    public LRIGA_B2_TamagoDrumroll()
    {
        setImageSets("WXDi-P02-027");
        
        setOriginalName("タマゴ＝ドラムロール");
        setAltNames("タマゴドラムロール Tamago Doramurooru");
        setDescription("jp",
                "@E：対戦相手のすべてのシグニをダウンする。"
        );
        
        setName("en", "Tamago =Drumroll=");
        setDescription("en",
                "@E: Down all SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Tamago-Drumroll");
        setDescription("en_fan",
                "@E: Down all of your opponent's SIGNI."
        );
        
		setName("zh_simplified", "玉子=敲敲");
        setDescription("zh_simplified", 
                "@E :对战对手的全部的精灵横置。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(6));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            down(getSIGNIOnField(getOpponent()));
        }
    }
}
