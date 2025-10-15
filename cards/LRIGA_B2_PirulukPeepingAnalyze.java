package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_B2_PirulukPeepingAnalyze extends Card {
    
    public LRIGA_B2_PirulukPeepingAnalyze()
    {
        setImageSets("WXDi-P08-036");
        
        setOriginalName("ピルルク/Ｐ－Ａ");
        setAltNames("ピルルクピーピングアナライズ Piruruku Piipingu Anaraizu PA P-A");
        setDescription("jp",
                "@E：対戦相手の手札を見て１枚選び、捨てさせる。\n" +
                "@E：対戦相手のルリグ１体を対象とし、それを凍結する。"
        );
        
        setName("en", "Piruluk / Peeping Analyze");
        setDescription("en",
                "@E: Look at your opponent's hand and choose a card. Your opponent discards it.\n" +
                "@E: Freeze target LRIG on your opponent's field."
        );
        
        setName("en_fan", "Piruluk/Peeping Analyze");
        setDescription("en_fan",
                "@E: Look at your opponent's hand, choose 1 card, and your opponent discards it.\n" +
                "@E: Target 1 of your opponent's LRIG, and freeze it."
        );
        
		setName("zh_simplified", "皮璐璐可/P-A");
        setDescription("zh_simplified", 
                "@E :看对战对手的手牌选1张，舍弃。\n" +
                "@E :对战对手的分身1只作为对象，将其冻结。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(2));
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
        }
    }
}
