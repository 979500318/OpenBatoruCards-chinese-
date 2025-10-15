package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_K2_AssistTokoLevel2 extends Card {
    
    public LRIGA_K2_AssistTokoLevel2()
    {
        setImageSets("WXDi-D02-10LA");
        
        setOriginalName("【アシスト】とこ　レベル２");
        setAltNames("アシストトコレベルニ Ashisuto Toko Reberu Ni Assist Toko");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらをバニッシュする。"
        );
        
        setName("en", "[Assist] Toko, Level 2");
        setDescription("en",
                "@E: Vanish up to two target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "[Assist] Toko Level 2");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and banish them."
        );
        
		setName("zh_simplified", "【支援】床 等级2");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，将这些破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BANISH).OP().SIGNI());
            banish(data);
        }
    }
}