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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W1_BootesNaturalStar extends Card {

    public SIGNI_W1_BootesNaturalStar()
    {
        setImageSets("WX24-P3-059");

        setOriginalName("羅星　ブーテス");
        setAltNames("ラセイブーテス Rasei Buutesu Bootes");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのデッキの一番上を公開する。その後、そのカードがレベル１のシグニの場合、あなたの＜宇宙＞のシグニを２体まで対象とし、次の対戦相手のターン終了時まで、それらのパワーを＋4000する。"
        );

        setName("en", "Boötes, Natural Star");
        setDescription("en",
                "@U: At the end of your turn, reveal the top card of your deck. If it is a level 1 SIGNI, target up to 2 of your <<Space>> SIGNI, and until the end of your opponent's next turn, they get +4000 power."
        );

		setName("zh_simplified", "罗星 牧夫座");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的牌组最上面公开。然后，那张牌是等级1的精灵的场合，你的<<宇宙>>精灵2只最多作为对象，直到下一个对战对手的回合结束时为止，这些的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);

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
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(cardIndex.getIndexedInstance().getLevelByRef() == 1)
                {
                    DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.SPACE));
                    gainPower(data, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
    }
}
