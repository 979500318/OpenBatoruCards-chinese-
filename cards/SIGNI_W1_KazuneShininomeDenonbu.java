package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W1_KazuneShininomeDenonbu extends Card {

    public SIGNI_W1_KazuneShininomeDenonbu()
    {
        setImageSets("WXDi-P14-075", "WXDi-P14-075P");

        setOriginalName("電音部　東雲和音");
        setAltNames("デンオンブシノノメカズネ Denonbu Shinonome Kazune");
        setDescription("jp",
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの一番上を公開する。そのカードが＜電音部＞のシグニの場合、カードを１枚引く。"
        );

        setName("en", "DEN-ON-BU Kazune Shinonome");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If that card is a <<DEN-ON-BU>> SIGNI, draw a card."
        );
        
        setName("en_fan", "Kazune Shinonome, Denonbu");
        setDescription("en_fan",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If it is a <<Denonbu>> SIGNI, draw 1 card."
        );

		setName("zh_simplified", "电音部 东云和音");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这只精灵在竖直状态的场合，你的牌组最上面公开。那张牌是<<電音部>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(!isState(CardStateFlag.DOWNED))
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DENONBU) || draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
