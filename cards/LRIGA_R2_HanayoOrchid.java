package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_HanayoOrchid extends Card {
    
    public LRIGA_R2_HanayoOrchid()
    {
        setImageSets("WXDi-D08-007");
        
        setOriginalName("花代・蘭");
        setAltNames("ハナヨラン Hanayo Ran");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X：対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Hanayo, Orchid");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E %X: Put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash."
        );
        
        setName("en_fan", "Hanayo Orchid");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E %X: Target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash."
        );
        
		setName("zh_simplified", "花代·兰");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %X:从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
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
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
            trash(target);
        }
    }
}
