package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B2_UmrBreak extends Card {
    
    public LRIGA_B2_UmrBreak()
    {
        setImageSets("WXDi-P00-017");
        
        setOriginalName("ウムル＝ブレイク");
        setAltNames("ウムルブレイク Umuru Bureiku");
        setDescription("jp",
                "@E：対戦相手の凍結状態のシグニを２体まで対象とし、それらをバニッシュする。"
        );
        
        setName("en", "Umr =Shatter=");
        setDescription("en",
                "@E: Vanish up to two target frozen SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Umr-Break");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's frozen SIGNI, and banish them."
        );
        
		setName("zh_simplified", "乌姆尔=破碎");
        setDescription("zh_simplified", 
                "@E :对战对手的冻结状态的精灵2只最多作为对象，将这些破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BANISH).OP().SIGNI().withState(CardStateFlag.FROZEN));
            banish(data);
        }
    }
}
