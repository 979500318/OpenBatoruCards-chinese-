package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_RavanaWickedDevilPrincess extends Card {

    public SIGNI_K3_RavanaWickedDevilPrincess()
    {
        setImageSets("WX24-P4-049");

        setOriginalName("凶魔姫　ラーヴァナ");
        setAltNames("キョウマキラーヴァナ Kyoumaki Raavana");
        setDescription("jp",
                "@U $T1：いずれかのプレイヤーがリフレッシュしたとき、【エナチャージ１】をする。\n" +
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを３枚トラッシュに置いてもよい。この方法でトラッシュに置かれたシグニのレベルの合計１につき対戦相手のデッキの上からカードを１枚トラッシュに置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Ravana, Wicked Devil Princess");
        setDescription("en",
                "@U $T1: When either player refreshes, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI attacks, you may put the top 3 cards of your deck into the trash. Put the top card of your opponent's deck into the trash for each total level of the SIGNI put into the trash this way." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "凶魔姬 罗波那");
        setDescription("zh_simplified", 
                "@U $T1 :当任一方的玩家重构时，[[能量填充1]]。\n" +
                "@U :当这只精灵攻击时，可以从你的牌组上面把3张牌放置到废弃区。依据这个方法放置到废弃区的精灵的等级的合计的数量，每有1级就从对战对手的牌组上面把1张牌放置到废弃区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.REFRESH, this::onAutoEff1);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff1(CardIndex caller)
        {
            enerCharge(1);
        }

        private void onAutoEff2()
        {
            if(playerChoiceActivate())
            {
                DataTable<CardIndex> data = millDeck(3);
                if(data.get() != null) millDeck(getOpponent(), data.stream().mapToInt(c -> c.getIndexedInstance().getLevelByRef()).sum());
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            } else {
                draw(1);
            }
        }
    }
}
