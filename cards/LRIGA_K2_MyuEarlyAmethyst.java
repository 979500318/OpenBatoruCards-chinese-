package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_K2_MyuEarlyAmethyst extends Card {

    public LRIGA_K2_MyuEarlyAmethyst()
    {
        setImageSets("WXDi-P13-041");

        setOriginalName("ミュウ　－　コムラサキ");
        setAltNames("ミュウコムラサキ Myuu Komurasaki");
        setDescription("jp",
                "@E @[手札を好きな枚数捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこの方法で捨てたカード１枚につき－10000する。"
        );

        setName("en", "Myu - Purple Emperor");
        setDescription("en",
                "@E @[Discard any number of cards]@: Target SIGNI on your opponent's field gets --10000 power for each card discarded this way until end of turn."
        );
        
        setName("en_fan", "Myu - Early Amethyst");
        setDescription("en_fan",
                "@E @[Discard any number of cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power for each card discarded this way."
        );

		setName("zh_simplified", "缪-细带闪蛱蝶");
        setDescription("zh_simplified", 
                "@E 手牌任意张数舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这个方法舍弃的牌的数量，每有1张就-10000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(new DiscardCost(0, AbilityConst.MAX_UNLIMITED), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000 * getAbility().getCostPaidData().size(), ChronoDuration.turnEnd());
        }
    }
}
