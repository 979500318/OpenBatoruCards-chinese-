package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_B3_YuukaHayaseSportswear extends Card {

    public SIGNI_B3_YuukaHayaseSportswear()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-043");

        setOriginalName("早瀬ユウカ(体操服)");
        setAltNames("ハヤセユウカタイソウフク Hayase Yuuka Taisoufuku");
        setDescription("jp",
                "@U：あなたのターン終了時、次の対戦相手のターン終了時まで、あなたのすべての＜ブルアカ＞のシグニのパワーをあなたの手札１枚につき＋1000する。\n" +
                "@A $T1 @[手札を１枚捨てる]@：あなたのデッキの上からカードを４枚見る。その中から＜ブルアカ＞のシグニを２枚まで場に出し、残りを好きな順番でデッキの一番下に置く。" +
                "~{{U：あなたのターン終了時、カードを１枚引く。"
        );

        setName("en", "Hayase Yuuka (Sportswear)");

        setName("en_fan", "Yuuka Hayase (Sportswear)");
        setDescription("en",
                "@U: At the end of your turn, until the end of your opponent's next turn, all of your <<Blue Archive>> SIGNI get +1000 power for each card in your hand.\n" +
                "@A $T1 @[Discard 1 card from your hand]@: Look at the top 4 cards of your deck. Put up to 2 <<Blue Archive>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order." +
                "~{{U: At the end of your turn, draw 1 card."
        );

		setName("zh_simplified", "早濑优香(体操服)");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，直到下一个对战对手的回合结束时为止，你的全部的<<ブルアカ>>精灵的力量依据你的手牌的数量，每有1张就+1000。\n" +
                "@A $T1 手牌1张舍弃:从你的牌组上面看4张牌。从中把<<ブルアカ>>精灵2张最多出场，剩下的任意顺序放置到牌组最下面。\n" +
                "~{{U:你的回合结束时，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new DiscardCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getExportedData(), 1000 * getHandCount(getOwner()), ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onActionEff()
        {
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked().playable());
            putOnField(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onAutoEff2(CardIndex caller)
        {
            draw(1);
        }
    }
}
