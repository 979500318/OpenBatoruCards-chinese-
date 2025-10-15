package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventRefresh;

public final class SIGNI_K3_AruRikuhachimaDress extends Card {

    public SIGNI_K3_AruRikuhachimaDress()
    {
        setImageSets("WX25-CP1-047");

        setOriginalName("陸八魔アル(ドレス)");
        setAltNames("リクハチマアルドレス Rikuhachima Aru Doresu");
        setDescription("jp",
                "@U $T1：あなたの他の＜ブルアカ＞のシグニ１体がアタックしたとき、あなたのトラッシュから黒の＜ブルアカ＞のシグニを３枚まで対象とし、それらを好きな順番でデッキの一番下に置く。この方法でデッキに移動したカード１枚につき対戦相手のデッキの上からカードを２枚トラッシュに置く。" +
                "~{{U：対戦相手がリフレッシュしたとき、【エナチャージ１】をする。@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Rikuhachima Aru (Dress)");

        setName("en", "Aru Rikuhachima (Dress)");
        setDescription("en",
                "@U $T1: When 1 of your other <<Blue Archive>> SIGNI attacks, target up to 3 black <<Blue Archive>> SIGNI from your trash, and put them on the bottom of your deck in any order. For each card put into your deck this way, put the top 2 cards of your opponent's deck into the trash." +
                "~{{U: Whenever your opponent refreshes, [[Ener Charge 1]].@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "陆八魔爱露(礼服)");
        setDescription("zh_simplified", 
                "@U $T1 :当你的其他的<<ブルアカ>>精灵1只攻击时，从你的废弃区把黑色的<<ブルアカ>>精灵3张最多作为对象，将这些任意顺序放置到牌组最下面。依据这个方法往牌组移动的牌的数量，每有1张就从对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "~{{U:当对战对手重构时，[[能量填充1]]。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.REFRESH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) && caller != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withColor(CardColor.BLACK).withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash());
            
            int count = returnToDeck(data, DeckPosition.BOTTOM);
            millDeck(getOpponent(), count * 2);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return ((EventRefresh)getEvent()).getPlayer() == getOpponent() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
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
