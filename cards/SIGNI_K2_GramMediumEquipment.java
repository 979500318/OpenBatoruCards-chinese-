package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_GramMediumEquipment extends Card {
    
    public SIGNI_K2_GramMediumEquipment()
    {
        setImageSets("WXDi-P01-084");
        
        setOriginalName("中装　グラム");
        setAltNames("チュウソウグラム Chuusou Guramu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );
        
        setName("en", "Gram, High Armed");
        setDescription("en",
                "@E %X: Target SIGNI on your opponent's field gets --3000 power until end of turn."
        );
        
        setName("en_fan", "Gram, Medium Equipment");
        setDescription("en_fan",
                "@E %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );
        
		setName("zh_simplified", "中装 古拉姆剑");
        setDescription("zh_simplified", 
                "@E %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
