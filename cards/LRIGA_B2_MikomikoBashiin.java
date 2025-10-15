package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_MikomikoBashiin extends Card {
    
    public LRIGA_B2_MikomikoBashiin()
    {
        setImageSets("WXDi-P07-024");
        
        setOriginalName("みこみこ☆ばしーん");
        setAltNames("ミコミコバシーン Mikomiko Bashiin");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "@E %B %X：対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、それをダウンする。"
        );
        
        setName("en", "Mikomiko☆Bashiin");
        setDescription("en",
                "@E: Down up to two target SIGNI on your opponent's field.\n" +
                "@E %B %X: Down target SIGNI on your opponent's field unless your opponent discards three cards."
        );
        
        setName("en_fan", "Mikomiko☆Bashiin");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "@E %B %X: Target 1 of your opponent's SIGNI, and down it unless your opponent discards 3 cards from their hand."
        );
        
		setName("zh_simplified", "美琴琴☆叭兮");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "@E %B%X对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么将其#D。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setCost(Cost.colorless(4));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
            {
                down(target);
            }
        }
    }
}
