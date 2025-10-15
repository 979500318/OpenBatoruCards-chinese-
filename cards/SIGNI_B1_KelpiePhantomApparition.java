package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_KelpiePhantomApparition extends Card {

    public SIGNI_B1_KelpiePhantomApparition()
    {
        setImageSets("WX25-P1-082");

        setOriginalName("幻怪　ケルピー");
        setAltNames("ゲンカイケルピー Genkai Kerupii");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにあなたがアーツを使用していた場合、あなたのデッキの一番上を公開する。そのカードが＜怪異＞のシグニの場合、カードを１枚引く。"
        );

        setName("en", "Kelpie, Phantom Apparition");
        setDescription("en",
                "@U: At the end of your turn, if you used ARTS this turn, reveal the top card of your deck. If it is a <<Apparition>> SIGNI, draw 1 card."
        );

		setName("zh_simplified", "幻怪 水栖马");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合你把必杀使用过的场合，你的牌组最上面公开。那张牌是<<怪異>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.APPARITION) || draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
