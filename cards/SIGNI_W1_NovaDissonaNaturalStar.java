package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W1_NovaDissonaNaturalStar extends Card {

    public SIGNI_W1_NovaDissonaNaturalStar()
    {
        setImageSets("WXDi-P12-057", "SPDi01-83");

        setOriginalName("羅星　ノヴァ//ディソナ");
        setAltNames("ラセイノヴァディソナ Rasei Nova Disona");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのデッキの一番上を公開する。そのカードが#Sの場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。"
        );

        setName("en", "Nova//Dissona, Natural Planet");
        setDescription("en",
                "@U: At the end of your turn, reveal the top card of your deck. If that card is #S, this SIGNI gets +5000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Nova//Dissona, Natural Star");
        setDescription("en_fan",
                "@U: At the end of your turn, reveal the top card of your deck. If it is a #S @[Dissona]@ card, until the end of your opponent's next turn, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "罗星 超//失调");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，你的牌组最上面公开。那张牌是#S的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            if(cardIndex != null && cardIndex.getIndexedInstance().isState(CardStateFlag.IS_DISSONA))
            {
                gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
            
            returnToDeck(cardIndex, DeckPosition.TOP);
        }
    }
}
