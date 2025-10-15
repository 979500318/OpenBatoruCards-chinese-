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

public final class ARTS_G_BusinessTripMomoyodouBeachShackFranchisePlan extends Card {

    public ARTS_G_BusinessTripMomoyodouBeachShackFranchisePlan()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-030");

        setOriginalName("出張！百夜堂 海の家FC計画");
        setAltNames("シュッチョウモモヨドウウミノイエフランチャイズケイカク  Shucchou Momoyodou Umi no Ie Furanchaizu Keikaku");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のルリグかシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。\n" +
                "$$2対戦相手のルリグとシグニを合計２体まで対象とし、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、このターン、それらがそれぞれ次にアタックしたとき、そのアタックを無効にする。"
        );

        setName("en", "Business Trip! Momoyodou Beach Shack Franchise Plan");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIG or SIGNI, and this turn, the next time it attacks, disable that attack.\n" +
                "$$2 Target up to 2 of your opponent's LRIG and/or SIGNI, and you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, this turn, the next time each of them attacks, disable that attack."
        );

		setName("zh_simplified", "出差！白夜堂 海之家的FC计划");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的分身或精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n" +
                "$$2 对战对手的分身和精灵合计2只最多作为对象，可以从你的能量区把<<ブルアカ>>牌2张放置到废弃区。这样做的场合，这个回合，当这些下一次攻击时，那次攻击无效。\n"
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
                disableNextAttack(target);
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().fromField());
                if(data.get() != null)
                {
                    DataTable<CardIndex> dataToTrash = playerTargetCard(0,2, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                    if(trash(dataToTrash) == 2) for(int i=0;i<data.size();i++) disableNextAttack(data.get(i));
                }
            }
        }
    }
}
