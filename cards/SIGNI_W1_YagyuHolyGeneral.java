package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W1_YagyuHolyGeneral extends Card {
    
    public SIGNI_W1_YagyuHolyGeneral()
    {
        setImageSets("WXDi-P07-051");
        
        setOriginalName("聖将　ヤギュウ");
        setAltNames("セイショウヤギュウ Seishou Yagyu");
        setDescription("jp",
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの一番上を公開する。そのカードが白の場合、カードを１枚引く。"
        );
        
        setName("en", "Yagyu, Blessed General");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If that card is white, draw a card."
        );
        
        setName("en_fan", "Yagyu, Holy General");
        setDescription("en_fan",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If it is a white card, draw 1 card."
        );
        
		setName("zh_simplified", "圣将 柳生宗矩");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这只精灵在竖直状态的场合，你的牌组最上面公开。那张牌是白色的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(2000);
        
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
            if(!getCardIndex().getIndexedInstance().isState(CardStateFlag.DOWNED))
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex != null)
                {
                    if(cardIndex.getIndexedInstance().getColor().matches(CardColor.WHITE))
                    {
                        draw(1);
                    } else {
                        returnToDeck(cardIndex, DeckPosition.TOP);
                    }
                }
            }
        }
    }
}
