package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_UmrFreeze extends Card {
    
    public LRIGA_B1_UmrFreeze()
    {
        setImageSets("WXDi-P00-016");
        
        setOriginalName("ウムル＝フリーズ");
        setAltNames("ウムルフリーズ Umuru Furiizu");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらを凍結する。対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Umr =Freeze=");
        setDescription("en",
                "@E: Freeze up to two target SIGNI on your opponent's field. Your opponent discards a card."
        );
        
        setName("en_fan", "Umr-Freeze");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and freeze them. Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "乌姆尔=冷冻");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，将这些冻结。对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FREEZE).OP().SIGNI());
            freeze(data);
            
            discard(getOpponent(), 1);
        }
    }
}
