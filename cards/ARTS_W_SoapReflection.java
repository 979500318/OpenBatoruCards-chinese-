package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.cost.EnerCost;

public final class ARTS_W_SoapReflection extends Card {

    public ARTS_W_SoapReflection()
    {
        setImageSets("WX25-P1-002", "WX25-P1-002U");

        setOriginalName("シャボン・レフレクション");
        setAltNames("シャボンレフレクション Shabon Refurekushon");
        setDescription("jp",
                "@[ブースト]@ -- %W %X %X\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。あなたがブーストしていた場合、それを手札に戻す。"
        );

        setName("en", "Soap Reflection");
        setDescription("en",
                "@[Boost]@ -- %W %X %X\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities. If you used Boost, return it to their hand."
        );

		setName("zh_simplified", "泡沫·折射");
        setDescription("zh_simplified", 
                "赋能—%W%X %X（这张必杀使用时，可以作为使用费用追加把%W%X %X:支付）\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。你赋能的场合，将其返回手牌。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            arts.setAdditionalCost(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)));
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            
            if(target != null)
            {
                disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
                if(arts.hasPaidAdditionalCost()) addToHand(target);
            }
        }
    }
}

