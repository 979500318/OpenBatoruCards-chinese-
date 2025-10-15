package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_MadokaDove extends Card {
    
    public LRIGA_B2_MadokaDove()
    {
        setImageSets("WXDi-D06-007");
        
        setOriginalName("マドカ//ダブ");
        setAltNames("マドカダブ Madoka Dabu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。カードを１枚引く。\n" +
                "@E %B：対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );
        
        setName("en", "Madoka//Dub");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field. Draw a card.\n" +
                "@E %B: Your opponent discards a card at random."
        );
        
        setName("en_fan", "Madoka//Dub");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it. Draw 1 card.\n" +
                "@E %B: Choose 1 card in your opponent's hand without looking, and discard it."
        );
        
		setName("zh_simplified", "円//温顺");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵1只作为对象，将其#D。抽1张牌。\n" +
                "@E %B:不看对战对手的手牌选1张，舍弃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
            
            draw(1);
        }
        
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}
