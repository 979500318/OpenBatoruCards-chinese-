package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_K3_HaritenGreatTrap extends Card {

    public SIGNI_K3_HaritenGreatTrap()
    {
        setImageSets("WXK01-064");

        setOriginalName("大罠　ハリテン");
        setAltNames("ダイビンハリテン Daibin Hariten");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを２枚トラッシュに置く。その後、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこの方法でトラッシュに置かれたシグニのレベルの合計１につき－1000する。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Hariten, Great Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put the top 2 cards of your deck into the trash. Then, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each level of the SIGNI put into the trash this way." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "大罠 针天井");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从你的牌组上面把2张牌放置到废弃区。然后，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这个方法放置到废弃区的精灵的等级的合计的数量，每有1级就-1000。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            DataTable<CardIndex> data = millDeck(2);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -1000 * data.stream().mapToInt(cardIndex -> cardIndex.getIndexedInstance().getLevelByRef()).sum(), ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -3000, ChronoDuration.turnEnd());
            } else {
                enerCharge(1);
            }
        }
    }
}
