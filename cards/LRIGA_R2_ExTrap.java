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

public final class LRIGA_R2_ExTrap extends Card {
    
    public LRIGA_R2_ExTrap()
    {
        setImageSets("WXDi-P05-017");
        
        setOriginalName("エクストラップ");
        setAltNames("Ekusu Torappu");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@U：このシグニがアタックしたとき、%X %X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。"
        );
        
        setName("en", "Ex Trap");
        setDescription("en",
                "@E: Up to two target SIGNI on your opponent's field gain@>@U: Whenever this SIGNI attacks, vanish it unless you pay %X %X %X.@@until end of turn."
        );
        
        setName("en_fan", "Ex Trap");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@U: Whenever this SIGNI attacks, unless you pay %X %X %X, banish this SIGNI."
        );
        
		setName("zh_simplified", "艾克斯陷阱");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X %X %X:支付，那么这只精灵破坏。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            
            if(data.get() != null)
            {
                for(int i=0;i<data.size();i++)
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(data.get(i), attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = getAbility().getSourceCardIndex();
            if(!cardIndex.getIndexedInstance().payEner(Cost.colorless(3)))
            {
                cardIndex.getIndexedInstance().banish(cardIndex);
            }
        }
    }
}
