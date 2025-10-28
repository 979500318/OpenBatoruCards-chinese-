package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.PrintedValue;

public final class ARTS_K_PurpleStorm extends Card {

    public ARTS_K_PurpleStorm()
    {
        setImageSets("WX24-P3-040", "SPDi37-17");

        setOriginalName("パープル・ストーム");
        setAltNames("パープルストーム Paapuru Sutoomu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "$$2表記されているパワーと異なるパワーの対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Purple Storm");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power different from its printed power, and banish it."
        );

        setName("zh_simplified", "紫色·风暴");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "$$2 与正面记载的力量不同力量的对战对手的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            arts.setModeChoice(1);
        }

        private void onARTSEff()
        {
            if(arts.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -8000, ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPrintedPower(PrintedValue.LOWER_THAN_CURRENT, PrintedValue.HIGHER_THAN_CURRENT)).get();
                banish(target);
            }
        }
    }
}
