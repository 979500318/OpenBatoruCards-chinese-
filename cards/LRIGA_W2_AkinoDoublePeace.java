package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_AkinoDoublePeace extends Card {
    
    public LRIGA_W2_AkinoDoublePeace()
    {
        setImageSets("WXDi-P01-010");
        
        setOriginalName("アキノ＊ダブルピース");
        setAltNames("アキノダブルピース Akino Daburu Piisu");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E %X %X @[手札を１枚捨てる]@：対戦相手のレベル２のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E %X %X @[手札を２枚捨てる]@：対戦相手のレベル３のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Akino*Peace!");
        setDescription("en",
                "@E: Return target level one SIGNI on your opponent's field to its owner's hand.\n" +
                "@E %X %X @[Discard a card]@: Return target level two SIGNI on your opponent's field to its owner's hand.\n" +
                "@E %X %X @[Discard two cards]@: Return target level three SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Akino*Double Peace");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and return it to their hand.\n" +
                "@E %X %X @[Discard 1 card from your hand]@: Target 1 of your opponent's level 2 SIGNI, and return it to their hand.\n" +
                "@E %X %X @[Discard 2 cards from your hand]@: Target 1 of your opponent's level 3 SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "昭乃＊耶耶");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其返回手牌。\n" +
                "@E %X %X手牌1张舍弃:对战对手的等级2的精灵1只作为对象，将其返回手牌。\n" +
                "@E %X %X手牌2张舍弃:对战对手的等级3的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
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
            registerEnterAbility(new AbilityCostList(new EnerCost(Cost.colorless(2)), new DiscardCost(1)), this::onEnterEff2);
            registerEnterAbility(new AbilityCostList(new EnerCost(Cost.colorless(2)), new DiscardCost(2)), this::onEnterEff3);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
            addToHand(target);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(2)).get();
            addToHand(target);
        }
        private void onEnterEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(3)).get();
            addToHand(target);
        }
    }
}
