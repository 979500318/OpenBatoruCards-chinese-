package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_B2_ReiSelflessCut extends Card {
    
    public LRIGA_B2_ReiSelflessCut()
    {
        setImageSets("WXDi-D03-010");
        
        setOriginalName("レイ＊無我斬");
        setAltNames("レイムガザン Rei Muga Zan");
        setDescription("jp",
                "@E：カードを２枚引く。対戦相手の手札を２枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Rei*Empty Blade");
        setDescription("en",
                "@E: Draw two cards. Your opponent discards two cards at random."
        );
        
        setName("en_fan", "Rei*Selfless Cut");
        setDescription("en_fan",
                "@E: Draw 2 cards. Choose 2 cards from your opponent's hand without looking, and discard them."
        );
        
		setName("zh_simplified", "令＊无我斩");
        setDescription("zh_simplified", 
                "@E :抽2张牌。不看对战对手的手牌选2张，舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
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
            draw(2);
            
            DataTable<CardIndex> data = playerChoiceHand(2);
            discard(data);
        }
    }
}
