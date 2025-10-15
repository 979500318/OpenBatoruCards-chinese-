package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class LRIGA_K1_MahomahoZugagaan extends Card {
    
    public LRIGA_K1_MahomahoZugagaan()
    {
        setImageSets("WXDi-P05-028");
        
        setOriginalName("まほまほ☆ずががーん");
        setAltNames("マホマホズガガーン Mahomaho zugagaan");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、対戦相手が手札を２枚捨てないかぎり、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Mahomaho☆Zugagaan");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --12000 power until end of turn unless your opponent discards two cards."
        );
        
        setName("en_fan", "Mahomaho☆Zugagaan");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and unless your opponent discards 2 cards from their hand, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "真帆帆☆哫呐嘎");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，如果对战对手不把手牌2张舍弃，那么直到回合结束时为止，其的力量-12000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).size() != 2)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
