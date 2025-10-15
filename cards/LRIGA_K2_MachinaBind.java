package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_MachinaBind extends Card {
    
    public LRIGA_K2_MachinaBind()
    {
        setImageSets("WXDi-P04-027");
        
        setOriginalName("マキナバインド");
        setAltNames("Makina Baindo");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－20000する。@@を得る。\n" +
                "@E %K %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－20000する。@@を得る。"
        );
        
        setName("en", "Machina Bind");
        setDescription("en",
                "@E: Up to two target SIGNI on your opponent's field gain@>@U: Whenever this SIGNI attacks, this SIGNI gets --20000 power until end of turn.@@until end of turn.\n" +
                "@E %K %X %X: Target SIGNI on your opponent's field gains@>@U: Whenever this SIGNI attacks, this SIGNI gets --20000 power until end of turn.@@until end of turn."
        );
        
        setName("en_fan", "Machina Bind");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, it gets --20000 power.@@" +
                "@E %K %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, it gets --20000 power."

        );
        
		setName("zh_simplified", "玛琪娜缠绕");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-20000。@@\n" +
                "@E %K%X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-20000。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(data.get() != null) for(int i=0;i<data.size();i++) onEnterEffAttachAutoAbility(data.get(i));
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) onEnterEffAttachAutoAbility(target);
        }
        
        private void onEnterEffAttachAutoAbility(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -20000, ChronoDuration.turnEnd());
        }
    }
}
