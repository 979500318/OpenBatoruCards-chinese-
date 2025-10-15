package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_TucanaNaturalStar extends Card {
    
    public SIGNI_K3_TucanaNaturalStar()
    {
        setImageSets("WXDi-P02-087");
        
        setOriginalName("羅星　トゥカナ");
        setAltNames("ラセイトゥカナ Rasei Tukana");
        setDescription("jp",
                "@E %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "Tucana, Natural Planet");
        setDescription("en",
                "@E %X %X: Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Tucana, Natural Star");
        setDescription("en_fan",
                "@E %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "罗星 巨嘴鸟座");
        setDescription("zh_simplified", 
                "@E %X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
