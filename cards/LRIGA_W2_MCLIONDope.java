package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_W2_MCLIONDope extends Card {
    
    public LRIGA_W2_MCLIONDope()
    {
        setImageSets("WXDi-P02-020");
        
        setOriginalName("MC.LION-DOPE");
        setAltNames("エムシーリドープ Emu Shii Rion Doopu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からカードを１枚まで手札に加え、シグニを２枚まで場に出し、残りを好きな順番でデッキの一番下に置く。そらのシグニの@E能力は発動しない。"
        );
        
        setName("en", "MC LION - DOPE");
        setDescription("en",
                "@E: Look at the top five cards of your deck. You may add up to one card from among them to your hand and put up to two SIGNI from among them onto your field. Put the rest on the bottom of your deck in any order. The @E abilities of SIGNI put onto your field this way do not activate."
        );
        
        setName("en_fan", "MC.LION - DOPE");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Add up to 1 card from among them to your hand, put up to 2 SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. The @E abilities of those SIGNI don't activate."
        );
        
		setName("zh_simplified", "MC.LION-DOPE");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面看5张牌。从中把牌1张最多加入手牌，精灵2张最多出场，剩下的任意顺序放置到牌组最下面。这些精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(5));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            putOnField(data, Enter.DONT_ACTIVATE);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
