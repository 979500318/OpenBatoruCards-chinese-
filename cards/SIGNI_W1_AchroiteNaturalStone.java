package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W1_AchroiteNaturalStone extends Card {

    public SIGNI_W1_AchroiteNaturalStone()
    {
        setImageSets("WX24-P1-053");

        setOriginalName("羅石　アクロアイト");
        setAltNames("ラセキアクロアイト Raseki Akuroaito");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにあなたが手札を１枚以上捨てていた場合、あなたのデッキの上からカードを３枚見る。その中から＜宝石＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Achroite, Natural Stone");
        setDescription("en",
                "@U: At the end of your turn, if you discarded 1 or more cards from your hand this turn, look at the top 3 cards of your deck. Reveal 1 <<Gem>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "罗石 电气石");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合你把手牌1张以上舍弃过的场合，从你的牌组上面看3张牌。从中把<<宝石>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 1)
            {
                look(3);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.GEM).fromLooked()).get();
                reveal(cardIndex);
                addToHand(cardIndex);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
    }
}
