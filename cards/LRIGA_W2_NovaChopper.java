package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_W2_NovaChopper extends Card {
    
    public LRIGA_W2_NovaChopper()
    {
        setImageSets("WXDi-D05-007");
        
        setOriginalName("ノヴァ＝チョッパー");
        setAltNames("ノヴァチョッパー Nova Choppaa");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からシグニ１枚を場に出し、残りを好きな順番でデッキの一番下に置く。そのシグニの@E能力は発動しない。"
        );
        
        setName("en", "Nova =Chopper=");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put a SIGNI from among them onto your field. Put the rest on the bottom of your deck in any order. The @E abilities of SIGNI put onto your field this way do not activate."
        );
        
        setName("en_fan", "Nova-Chopper");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put up to 1 SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. That SIGNI's @E abilities don't activate."
        );
        
		setName("zh_simplified", "超=锯式");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面看5张牌。从中把精灵1张最多出场，剩下的任意顺序放置到牌组最下面。那只精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            putOnField(cardIndex, Enter.DONT_ACTIVATE);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
