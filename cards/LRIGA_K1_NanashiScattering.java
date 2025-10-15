package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K1_NanashiScattering extends Card {
    
    public LRIGA_K1_NanashiScattering()
    {
        setImageSets("WXDi-P07-034");
        
        setOriginalName("ナナシ・散布");
        setAltNames("ナナシサンプ Nanashi Sanpu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "@E %X：対戦相手のすべてのシグニゾーンに【ウィルス】を１つずつ置く。"
        );
        
        setName("en", "Nanashi Scattering");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --8000 power until end of turn.\n" +
                "@E %X: Put a [[Virus]] in each of your opponent's SIGNI Zones."
        );
        
        setName("en_fan", "Nanashi Scattering");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@E %X: Put 1 [[Virus]] on all of your opponent's SIGNI zones."
        );
        
		setName("zh_simplified", "无名·散布");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@E %X:对战对手的全部的精灵区各放置[[病毒]]1个。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            forEachSIGNIZone(getOpponent(), fieldZone -> attachZoneObject(fieldZone, CardUnderType.ZONE_VIRUS));
        }
    }
}
