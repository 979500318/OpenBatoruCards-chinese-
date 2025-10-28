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

public final class ARTS_R_PhoenixOfAuthority extends Card {

    public ARTS_R_PhoenixOfAuthority()
    {
        setImageSets("WX24-P3-034");

        setOriginalName("炎官鳥");
        setAltNames("エンカンチョウ Enkanchou");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2あなたの手札が０枚の場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Phoenix of Authority");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "$$2 If there are 0 cards in your hand, target 1 of your opponent's SIGNI, and banish it."
        );

        setName("zh_simplified", "炎官鸟");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 你的手牌在0张的场合，对战对手的精灵1只作为对象，将其破坏。"
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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                banish(target);
            } else if(getHandCount(getOwner()) == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            }
        }
    }
}
