package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;

public final class SIGNI_K2_SumieWickedBeauty extends Card {

    public SIGNI_K2_SumieWickedBeauty()
    {
        setImageSets("SPDi01-119");

        setOriginalName("凶美　スミエ");
        setAltNames("キョウビスミエ Kyoubi Sumie");
        setDescription("jp",
                "@E：対戦相手のトラッシュからシグニ１枚を対象とし、それをデッキの一番下に置く。その後、以下の２つから１つを選ぶ。\n" +
                "$$1この効果でデッキに移動したシグニと共通する色を持つ対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "$$2この効果でデッキに移動したシグニと同じカード名の対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Sumi-e, Wicked Beauty");
        setDescription("en",
                "@E: Target 1 SIGNI from your opponent's trash, and put it on the bottom of their deck. Then, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI that shares a common color with the SIGNI put into the deck this way, and until end of turn, it gets --2000 power.\n" +
                "$$2 Target 1 of your opponent's SIGNI with the same card name as the SIGNI put into the deck this way, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "凶美 墨绘");
        setDescription("zh_simplified", 
                "@E :从对战对手的废弃区把精灵1张作为对象，将其放置到牌组最下面。然后，从以下的2种选1种。\n" +
                "$$1 持有与这个效果往牌组移动的精灵共通颜色的对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "$$2 与这个效果往牌组移动的精灵相同牌名的对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().fromTrash()).get();
            
            if(target != null)
            {
                CardDataColor color = target.getIndexedInstance().getColor();
                String name = target.getIndexedInstance().getName().getValue();
                
                if(returnToDeck(target, DeckPosition.BOTTOM))
                {
                    if(playerChoiceMode() == 1)
                    {
                        target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withColor(color)).get();
                        gainPower(target, -2000, ChronoDuration.turnEnd());
                    } else {
                        target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withName(name)).get();
                        gainPower(target, -3000, ChronoDuration.turnEnd());
                    }
                }
            }
        }
    }
}
