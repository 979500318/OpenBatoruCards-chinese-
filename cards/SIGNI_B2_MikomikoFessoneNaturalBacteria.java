package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_MikomikoFessoneNaturalBacteria extends Card {

    public SIGNI_B2_MikomikoFessoneNaturalBacteria()
    {
        setImageSets("WXDi-P14-060");

        setOriginalName("羅菌　みこみこ//フェゾーネ");
        setAltNames("ラキンミコミコフェゾーネ Rakin Mikomiko Frezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手の手札を１枚見ないで選び、対戦相手はそのカードを公開する。あなたはそのカードを捨てさせてもよい。そうした場合、対戦相手はカードを１枚引く。"
        );

        setName("en", "Mikomiko//Fesonne, Natural Bacteria");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent chooses a card at random and reveals that card. You may force them to discard it. If you do, your opponent draws a card."
        );
        
        setName("en_fan", "Mikomiko//Fessone, Natural Bacteria");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, choose 1 card from your opponent's hand without looking, and your opponent reveals it, and you may make your opponent discard it. If you do, your opponent draws 1 card."
        );

		setName("zh_simplified", "罗菌 美琴琴//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，不看对战对手的手牌选1张，对战对手把那张牌公开。你可以把那张牌舍弃。这样做的场合，对战对手抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(2);
        setPower(8000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerChoiceHand().get();
            
            if(reveal(cardIndex) && playerChoiceActivate() && discard(cardIndex).get() != null)
            {
                draw(getOpponent(), 1);
            } else {
                addToHand(cardIndex);
            }
        }
    }
}
