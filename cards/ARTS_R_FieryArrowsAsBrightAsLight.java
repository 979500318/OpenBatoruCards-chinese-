package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_R_FieryArrowsAsBrightAsLight extends Card {

    public ARTS_R_FieryArrowsAsBrightAsLight()
    {
        setImageSets("WX24-P4-029");

        setOriginalName("光火矢如");
        setAltNames("コウヒシジョ Kouhishijo");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、%X %X %X %Xを支払わないかぎり、このシグニをバニッシュする。」を得る。\n" +
                "$$2対戦相手の白か青のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Fiery Arrows as Bright as Light");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, banish this SIGNI unless your opponent pays %X %X %X %X.@@" +
                "$$2 Target 1 of your opponent's white or blue SIGNI, and banish it."
        );

        setName("zh_simplified", "光火矢如");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其得到" +
                "@>@U :当这只精灵攻击时，如果不把%X %X %X %X支付，那么这只精灵破坏。@@" +
                "$$2 对战对手的白色或蓝色的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                attachAbility(target, new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff), ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withColor(CardColor.WHITE, CardColor.BLUE)).get();
                banish(target);
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            if(!source.getIndexedInstance().payEner(Cost.colorless(4)))
            {
                source.getIndexedInstance().banish(source);
            }
        }
    }
}
