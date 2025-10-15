package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_SangaSwing extends Card {
    
    public LRIGA_G1_SangaSwing()
    {
        setImageSets("WXDi-P01-030");
        
        setOriginalName("サンガ／／スイング");
        setAltNames("サンガスイング Sanga Suingu");
        setDescription("jp",
                "@E：あなたのトラッシュから#Gを持たないカードを２枚まで対象とし、それらをエナゾーンに置く。"
        );
        
        setName("en", "Sanga//Swing");
        setDescription("en",
                "@E: Put up to two target cards without a #G from your trash into your Ener Zone."
        );
        
        setName("en_fan", "Sanga//Swing");
        setDescription("en_fan",
                "@E: Target up to 2 cards without #G @[Guard]@ from your trash, and put them into your ener zone."
        );
        
		setName("zh_simplified", "山河//摇荡");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有#G的牌2张最多作为对象，将这些放置到能量区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setLevel(1);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            putInEner(data);
        }
    }
}
