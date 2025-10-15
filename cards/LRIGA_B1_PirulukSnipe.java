package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_PirulukSnipe extends Card {
    
    public LRIGA_B1_PirulukSnipe()
    {
        setImageSets("WXDi-P08-033");
        
        setOriginalName("ピルルク/ＳＮＩＰＥ");
        setAltNames("ピルルクスナイプ Piruruku Snunaipu");
        setDescription("jp",
                "@E：対戦相手の手札を見て[ガード]を持たないカード１枚を選び、捨てさせる。"
        );
        
        setName("en", "Piruluk / Snipe");
        setDescription("en",
                "@E: Look at your opponent's hand and choose a card without a #G. Your opponent discards it. "
        );
        
        setName("en_fan", "Piruluk/SNIPE");
        setDescription("en_fan",
                "@E: Look at your opponent's hand, choose 1 card without #G @[Guard]@, and your opponent discards it."
        );
        
		setName("zh_simplified", "皮璐璐可/SNIPE");
        setDescription("zh_simplified", 
                "@E 看对战对手的手牌选不持有#G的牌1张，舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PIRULUK);
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
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}
