package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class ARTS_R_MightyFlash extends Card {

    public ARTS_R_MightyFlash()
    {
        setImageSets("WD17-007", "WXK01-011");

        setOriginalName("一騎当閃");
        setAltNames("イッキトウセン Ikki Tousen");
        setDescription("jp",
                "@[ベット]@ -- #C\n\n" +
                "対戦相手のパワー7000以下のシグニ１体を対象とし、それをバニッシュする。あなたがベットしていた場合、代わりに対戦相手のパワー20000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Mighty Flash");
        setDescription("en",
                "@[Bet]@ -- #C\n\n" +
                "Target 1 of your opponent's SIGNI with power 7000 or less, and banish it. If you bet, instead, target 1 of your opponent's SIGNI with power 20000 or less, and banish it."
        );

        setName("zh_simplified", "一骑当闪");
        setDescription("zh_simplified", 
                "下注—#C\n" +
                "对战对手的力量7000以下的精灵1只作为对象，将其破坏。你下注的场合，作为替代，对战对手的力量20000以下的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(1));
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, !arts.hasUsedBet() ? 7000 : 20000)).get();
            banish(target);
        }
    }
}
