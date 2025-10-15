package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B2_MikomikoZubashaan extends Card {
    
    public LRIGA_B2_MikomikoZubashaan()
    {
        setImageSets("WXDi-P07-025");
        
        setOriginalName("みこみこ☆ずばしゃーん");
        setAltNames("ミコミコズバシャーン Mikomiko Zubashaan");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "@E @[手札を３枚捨てる]@：対戦相手の手札を見て１枚選び、捨てさせる。"
        );
        
        setName("en", "Mikomiko☆Zubashaan");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field on the bottom of its owner's deck.\n" +
                "@E @[Discard three cards]@: Look at your opponent's hand and choose a card. Your opponent discards it."
        );
        
        setName("en_fan", "Mikomiko☆Zubashaan");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and put it on the bottom of their deck.\n" +
                "@E @[Discard 3 cards from your hand]@: Look at your opponent's hand, choose 1 card from it, and discard it."
        );
        
		setName("zh_simplified", "美琴琴☆哫吧桑");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "@E 手牌3张舍弃:看对战对手的手牌选1张，舍弃。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setCost(Cost.colorless(3));
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            registerEnterAbility(new DiscardCost(3), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
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
