package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIGA_R2_LaylaTheWildDance extends Card {
    
    public LRIGA_R2_LaylaTheWildDance()
    {
        setImageSets("WXDi-P12-031");
        
        setOriginalName("レイラ・ザ・乱舞");
        setAltNames("レイラザランブ Reira Za Ranbu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E @[手札とエナゾーンにあるすべてのカードをトラッシュに置く]@：この方法でカードが６枚以上トラッシュに置かれた場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Layla the Ranbu");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n@E @[Put all cards in your hand and Ener Zone into your trash]@: If six or more cards were put into your trash this way, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Layla the Wild Dance");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E @[Put all cards from your hand and ener zone into the trash]@: If 6 or more cards were put into the trash this way, target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "蕾拉·极·乱舞");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E 手牌和能量区的全部的牌放置到废弃区:这个方法把牌6张以上放置到废弃区的场合，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LAYLA);
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

            registerEnterAbility(this::onEnterEff1);
            
            registerEnterAbility(new AbilityCostList(
                new DiscardCost(() -> getHandCount(getOwner())),
                new TrashCost(() -> getEnerCount(getOwner()), new TargetFilter().own().fromEner())
            ), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            if((getAbility().getCostPaidData(0,0) != null && getAbility().getCostPaidData(0,0).get() instanceof CardIndexSnapshot ? getAbility().getCostPaidData(0,0).size() : 0) +
               (getAbility().getCostPaidData(0,1) != null && getAbility().getCostPaidData(0,1).get() instanceof CardIndexSnapshot ? getAbility().getCostPaidData(0,1).size() : 0) >= 6)
            {
                onEnterEff1();
            }
        }
    }
}
