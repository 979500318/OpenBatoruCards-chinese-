package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_AssistAngeLevel1 extends Card {
    
    public LRIGA_W1_AssistAngeLevel1()
    {
        setImageSets("WXDi-D02-06LT");
        
        setOriginalName("【アシスト】アンジュ　レベル１");
        setAltNames("アシストアンジュレベルイチ Ashisuto Anju Reberu Ichi Assist Ange");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "[Assist] Ange Level 1");
        setDescription("en",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "【支援】安洁 等级1");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(cardIndex);
        }
    }
}
