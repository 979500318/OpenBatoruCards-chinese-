package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_MahomahoDogaan extends Card {
    
    public LRIGA_K2_MahomahoDogaan()
    {
        setImageSets("WXDi-P05-029");
        
        setOriginalName("まほまほ☆どがーん");
        setAltNames("マホマホドガーン Mahomaho Dogaan");
        setDescription("jp",
                "@E %X %X %X：あなたのトラッシュから#Gを持たないシグニを２枚まで対象とし、それらを手札に加える。\n" +
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの手札１枚につき－3000する。"
        );
        
        setName("en", "Mahomaho☆Dogaan");
        setDescription("en",
                "@E %X %X %X: Add up to two target SIGNI without a #G from your trash to your hand.\n" +
                "@E: Target SIGNI on your opponent's field gets --3000 power until end of turn for every card in your hand."
        );
        
        setName("en_fan", "Mahomaho☆Dogaan");
        setDescription("en_fan",
                "@E %X %X %X: Target up to 2 SIGNI without #G @[Guard]@ from your trash, and add them to your hand.\n" +
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power for each card in your hand."
        );
        
		setName("zh_simplified", "真帆帆☆咚嘎");
        setDescription("zh_simplified", 
                "@E %X %X %X从你的废弃区把不持有#G的精灵2张最多作为对象，将这些加入手牌。\n" +
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的手牌的数量，每有1张就-3000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            addToHand(data);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000 * getHandCount(getOwner()), ChronoDuration.turnEnd());
        }
    }
}
