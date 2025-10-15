package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_B2_UmrOutsider extends Card {
    
    public LRIGA_B2_UmrOutsider()
    {
        setImageSets("WXDi-P04-030");
        
        setOriginalName("ウムル＝アウトサイダー");
        setAltNames("ウムルアウトサイダー Umuru Autosaidaa");
        setDescription("jp",
                "@E：対戦相手のすべてのシグニをダウンする。"
        );
        
        setName("en", "Umr =Outsider=");
        setDescription("en",
                "@E: Down all SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Umr-Outsider");
        setDescription("en_fan",
                "@E: Down all of your opponent's SIGNI."
        );
        
		setName("zh_simplified", "乌姆尔=局外");
        setDescription("zh_simplified", 
                "@E 对战对手的全部的精灵#D。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(6));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            down(getSIGNIOnField(getOpponent()));
        }
    }
}
