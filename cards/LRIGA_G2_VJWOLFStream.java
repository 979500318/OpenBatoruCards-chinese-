package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_G2_VJWOLFStream extends Card {
    
    public LRIGA_G2_VJWOLFStream()
    {
        setImageSets("WXDi-P01-019");
        
        setOriginalName("VJ.WOLF-STREAM");
        setAltNames("ブイジェーウルフストリーム Buijee Urufu Sutoriimu");
        setDescription("jp",
                "@E：対戦相手のパワー13000以上のすべてのシグニをバニッシュする。"
        );
        
        setName("en", "VJ WOLF - STREAM");
        setDescription("en",
                "@E: Vanish all SIGNI on your opponent's field with power 13000 or more."
        );
        
        setName("en_fan", "VJ.WOLF - STREAM");
        setDescription("en_fan",
                "@E: Banish all of your opponent's SIGNI with power 13000 or more."
        );
        
		setName("zh_simplified", "VJ.WOLF-STREAM");
        setDescription("zh_simplified", 
                "@E :对战对手的力量13000以上的全部的精灵破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
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
            banish(new TargetFilter().OP().SIGNI().withPower(13000,0).getExportedData());
        }
    }
}
