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

public final class SIGNI_B1_CodeAntiMadokaDissona extends Card {

    public SIGNI_B1_CodeAntiMadokaDissona()
    {
        setImageSets("WXDi-P12-070", "SPDi01-86");

        setOriginalName("コードアンチ　マドカ//ディソナ");
        setAltNames("コードアンチマドカディソナ Koodo Anchi Madoka Disona");
        setDescription("jp",
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの一番上を公開する。そのカードが#Sの場合、カードを１枚引く。"
        );

        setName("en", "Madoka//Dissona, Code: Anti");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If that card is #S, draw a card."
        );
        
        setName("en_fan", "Code Anti Madoka//Dissona");
        setDescription("en_fan",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If it is a #S @[Dissona]@ SIGNI, add it to your hand."
        );

		setName("zh_simplified", "古兵代号 円//失调");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，这只精灵在竖直状态的场合，你的牌组最上面公开。那张牌是#S的场合，抽1张牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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
            if(!isState(CardStateFlag.DOWNED))
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex == null || !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) ||
                   !cardIndex.getIndexedInstance().isState(CardStateFlag.IS_DISSONA) || !addToHand(cardIndex))
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
