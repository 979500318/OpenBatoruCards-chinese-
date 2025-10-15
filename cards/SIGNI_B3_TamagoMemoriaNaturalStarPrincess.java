package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B3_TamagoMemoriaNaturalStarPrincess extends Card {
    
    public SIGNI_B3_TamagoMemoriaNaturalStarPrincess()
    {
        setImageSets("WXDi-P08-042", "WXDi-P08-042P");
        
        setOriginalName("羅星姫　タマゴ//メモリア");
        setAltNames("ラセイキタマゴメモリア Raseiki Tamago Memoria");
        setDescription("jp",
                "@U：あなたのターン終了時、カードを１枚引く。\n" +
                "@U：対戦相手のターン終了時、対戦相手の手札を見ないで１枚選び、捨てさせる。\n" +
                "@E #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋8000する。"
        );
        
        setName("en", "Tamago//Memoria, Galactic Queen");
        setDescription("en",
                "@U: At the end of your turn, draw a card.\n" +
                "@U: At the end of your opponent's turn, your opponent discards a card at random.\n" +
                "@E #D: This SIGNI gets +8000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Tamago//Memoria, Natural Star Princess");
        setDescription("en_fan",
                "@U: At the end of your turn, draw 1 card.\n" +
                "@U: At the end of your opponent's turn, choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@E #D: Until the end of your opponent's next turn, this SIGNI gets +8000 power."
        );
        
		setName("zh_simplified", "罗星姬 玉子//回忆");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，抽1张牌。\n" +
                "@U :对战对手的回合结束时，不看对战对手的手牌选1张，舍弃。\n" +
                "@E #D:直到下一个对战对手的回合结束时为止，这只精灵的力量+8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerEnterAbility(new DownCost(), this::onEnterEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(1);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
        
        private void onEnterEff()
        {
            gainPower(getCardIndex(), 8000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
