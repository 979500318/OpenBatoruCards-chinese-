package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_MikamuneSmallEquipment extends Card {

    public SIGNI_R1_MikamuneSmallEquipment()
    {
        setImageSets("WXDi-P11-052", "SPDi38-01");

        setOriginalName("小装　ミカムネ");
        setAltNames("ショウソウミカムネ Shousou Mikamune");
        setDescription("jp",
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの一番上を公開する。そのカードが＜アーム＞か＜ウェポン＞のシグニの場合、カードを１枚引く。"
        );

        setName("en", "Mikamune, Lightly Armed");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If that card is an <<Armed>> or a <<Weapon>> SIGNI, draw a card."
        );
        
        setName("en_fan", "Mikamune, Small Equipment");
        setDescription("en_fan",
                "@U: At the end of your turn, if this SIGNI is upped, reveal the top card of your deck. If it is an <<Arm>> or a <<Weapon>> SIGNI, draw 1 card."
        );

		setName("zh_simplified", "小装 三日月宗近");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这只精灵在竖直状态的场合，你的牌组最上面公开。那张牌是<<アーム>>或<<ウェポン>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
                
                if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) &&
                   cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ARM, CardSIGNIClass.WEAPON))
                {
                    draw(1);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
