package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B2_TamagoCymbalRoll extends Card {
    
    public LRIGA_B2_TamagoCymbalRoll()
    {
        setImageSets("WXDi-P03-025");
        
        setOriginalName("タマゴ＝シンバルロール");
        setAltNames("タマゴシンバルロール Tamago Shinbaru Rooru");
        setDescription("jp",
                "@E：カードを２枚引く。対戦相手の手札を見る。\n" +
                "@E @[手札を１枚捨てる]@：対戦相手の手札を見て１枚選び、捨てさせる。"
        );
        
        setName("en", "Tamago =Cymbal Roll=");
        setDescription("en",
                "@E: Draw two cards. Look at your opponent's hand.\n" +
                "@E @[Discard a card]@: Look at your opponent's hand and choose a card. Your opponent discards it."
        );
        
        setName("en_fan", "Tamago-Cymbal Roll");
        setDescription("en_fan",
                "@E: Draw 2 cards. Look at your opponent's hand.\n" +
                "@E @[Discard 1 card from your hand]@: Look at your opponent's hand and choose 1 card from it, and discard it."
        );
        
		setName("zh_simplified", "玉子=镲片");
        setDescription("zh_simplified", 
                "@E :抽2张牌。看对战对手的手牌。\n" +
                "@E 手牌1张舍弃:看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new DiscardCost(1), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            draw(2);
            
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            addToHand(getCardsInRevealed(getOpponent()));
        }
        
        private void onEnterEff2()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().fromRevealed()).get();
            discard(cardIndex);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}
