package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_AkinoRock extends Card {
    
    public LRIGA_W1_AkinoRock()
    {
        setImageSets("WXDi-D03-006");
        
        setOriginalName("アキノ＊グー");
        setAltNames("アキノグー Akino Guu");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Akino*Rock");
        setDescription("en",
                "@E: Return target level two or less SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Akino*Rock");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "昭乃＊石头");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(target);
        }
    }
}
