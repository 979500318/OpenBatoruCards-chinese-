package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_W2_AssistAngeLevel2 extends Card {
    
    public LRIGA_W2_AssistAngeLevel2()
    {
        setImageSets("WXDi-D02-07LT");
        
        setOriginalName("【アシスト】アンジュ　レベル２");
        setAltNames("アシストアンジュレベルニ Ashisuto Anju Reberu Ni Assist Ange");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それをトラッシュに置く。対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "[Assist] Ange, Level 2");
        setDescription("en",
                "@E: Put target level two or less SIGNI on your opponent's field into its owner's trash. Return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "[Assist] Ange Level 2");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and put it into the trash. Target 1 of your opponent's SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "【支援】安洁 等级2");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其放置到废弃区。对战对手的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
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
            CardIndex cardIndexToTrash = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            trash(cardIndexToTrash);
            
            CardIndex cardIndexToHand = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(cardIndexToHand);
        }
    }
}
