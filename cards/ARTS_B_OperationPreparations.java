package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;

public final class ARTS_B_OperationPreparations extends Card {

    public ARTS_B_OperationPreparations()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-028");

        setOriginalName("作戦準備");
        setAltNames("サクセンジュンビ Sakusen Junbi");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のルリグかシグニ１体を対象とし、それをダウンする。\n" +
                "$$2対戦相手のルリグとシグニを合計２体まで対象とし、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、それらをダウンする。"
        );

        setName("en", "Operation Preparations");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIG or SIGNI, and down it.\n" +
                "$$2 Target up to 2 LRIG and/or SIGNI, and you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, down them."
        );

        setName("zh_simplified", "作战准备");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的分身或精灵1只作为对象，将其横置。\n" +
                "$$2 对战对手的分身和精灵合计2只最多作为对象，可以从你的能量区把<<蔚蓝档案>>牌2张放置到废弃区。这样做的场合，将这些横置。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));
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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
                down(target);
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().fromField());
                if(data.get() != null)
                {
                    DataTable<CardIndex> dataToTrash = playerTargetCard(0,2, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                    if(trash(dataToTrash) == 2)
                    {
                        down(data);
                    }
                }
            }
        }
    }
}
