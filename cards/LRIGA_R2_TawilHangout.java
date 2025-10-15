package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_R2_TawilHangout extends Card {
    
    public LRIGA_R2_TawilHangout()
    {
        setImageSets("WXDi-P04-029");
        
        setOriginalName("タウィル＝ハングアウト");
        setAltNames("タウィルハングアウト Tauiru Hanguauto");
        setDescription("jp",
                "@E：対戦相手のシグニを１体を対象とし、それをバニッシュする。\n" +
                "@E @[手札を３枚まで捨てる]@：この方法で捨てたカードの枚数に等しい枚数のカードを引く。"
        );
        
        setName("en", "Tawil =Hangout=");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E @[Discard up to three cards]@: Draw cards equal to the number of cards discarded this way."
        );
        
        setName("en_fan", "Tawil-Hangout");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E @[Discard up to 3 cards from your hand]@: Draw cards equal to the number of cards discarded this way."
        );
        
		setName("zh_simplified", "塔维尔=聚集");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E 手牌3张最多舍弃:抽这个方法舍弃的牌的张数相等张数的牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
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
            registerEnterAbility(new DiscardCost(0,3), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                draw(getAbility().getCostPaidData().size());
            }
        }
    }
}
