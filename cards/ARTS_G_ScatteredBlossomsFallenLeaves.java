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

public final class ARTS_G_ScatteredBlossomsFallenLeaves extends Card {

    public ARTS_G_ScatteredBlossomsFallenLeaves()
    {
        setImageSets("WX24-P4-033");

        setOriginalName("飛花落葉");
        setAltNames("サーフィングファン Saafingu Fan Surfing Fan");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。\n" +
                "$$2対戦相手の青か黒のシグニ１体を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "Scattered Blossoms, Fallen Leaves");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and this turn, the next time it attacks, disable that attack.\n" +
                "$$2 Target 1 of your opponent's blue or black SIGNI, and put it into the ener zone."
        );

		setName("zh_simplified", "飞花落叶");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n" +
                "$$2 对战对手的蓝色或黑色的精灵1只作为对象，将其放置到能量区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
                disableNextAttack(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withColor(CardColor.BLUE, CardColor.BLACK)).get();
                putInEner(target);
            }
        }
    }
}
