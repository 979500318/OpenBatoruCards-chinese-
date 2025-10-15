package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_MadokaClap extends Card {
    
    public LRIGA_B2_MadokaClap()
    {
        setImageSets("WXDi-P01-029");
        
        setOriginalName("マドカ／／クラップ");
        setAltNames("マドカクラップ Madoka Kurappu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E @[手札を２枚捨てる]@：対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E %B %X %X：対戦相手のシグニ１体を対象とし、それをダウンする。"
        );
        
        setName("en", "Madoka//Clap");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field.\n" +
                "@E @[Discard two cards]@: Down target SIGNI on your opponent's field.\n" +
                "@E %B %X %X: Down target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Madoka//Clap");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it.\n" +
                "@E @[Discard 2 cards from your hand]@: Target 1 of your opponent's SIGNI, and down it.\n" +
                "@E %B %X %X: Target 1 of your opponent's SIGNI, and down it."
        );
        
		setName("zh_simplified", "円//拍掌");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵1只作为对象，将其#D。\n" +
                "@E :手牌2张舍弃对战对手的精灵1只作为对象，将其#D。\n" +
                "@E %B%X %X对战对手的精灵1只作为对象，将其#D。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(2));
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
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
        }
    }
}
