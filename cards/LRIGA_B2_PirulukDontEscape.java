package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;

public final class LRIGA_B2_PirulukDontEscape extends Card {
    
    public LRIGA_B2_PirulukDontEscape()
    {
        setImageSets("WXDi-P08-034");
        
        setOriginalName("ピルルク/Ｄ－Ｅ");
        setAltNames("ピルルクドントエスケープ Piruruku Donto Esukeepu DE D-E Dont Escape");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E %B %X %X %X %X：レベルの合計が４以下になるように対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );
        
        setName("en", "Piruluk / Don't Escape");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field.\n" +
                "@E %B %X %X %X %X: Down up to two target SIGNI on your opponent's field with a total level of four or less."
        );
        
        setName("en_fan", "Piruluk/Don't Escape");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it.\n" +
                "@E %B %X %X %X %X: Target up to 2 of your opponent's SIGNI with total level of 4 or less, and down them."
        );
        
		setName("zh_simplified", "皮璐璐可/D-E");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵1只作为对象，将其#D。\n" +
                "@E %B%X %X %X %X等级的合计在4以下的对战对手的精灵2只最多作为对象，将这些#D。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(4)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
        }
        
        private void onEnterEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI(), this::onEnterEff2TargetCond);
            down(data);
        }
        private boolean onEnterEff2TargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).sum() <= 4;
        }
    }
}
