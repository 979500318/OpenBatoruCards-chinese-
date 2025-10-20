package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_PirulukDontMove extends Card {
    
    public LRIGA_B2_PirulukDontMove()
    {
        setImageSets("WXDi-P08-035");
        
        setOriginalName("ピルルク/Ｄ－Ｍ");
        setAltNames("ピルルクドントムーブ Piruruku Donto Muubu DM D-M Dont Move");
        setDescription("jp",
                "@E：対戦相手のシグニ２体まで対象とし、それらをダウンする。\n" +
                "@E %X %X：カードを２枚引く。"
        );
        
        setName("en", "Piruluk / Don't Move");
        setDescription("en",
                "@E: Down up to two target SIGNI on your opponent's field.\n" +
                "@E %X %X: Draw two cards."
        );
        
        setName("en_fan", "Piruluk/Don't Move");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "@E %X %X: Draw 2 cards."
        );
        
		setName("zh_simplified", "皮璐璐可/D-M");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "@E %X %X:抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(3));
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
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
        
        private void onEnterEff2()
        {
            draw(2);
        }
    }
}
