package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W3_YukimeDissonaHolyGeneral extends Card {

    public SIGNI_W3_YukimeDissonaHolyGeneral()
    {
        setImageSets("WXDi-P12-061", "SPDi01-84");

        setOriginalName("聖将　ゆきめ//ディソナ");
        setAltNames("セイショウユキメディソナ Seishou Yukime Disona");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、このシグニを場からトラッシュに置いてもよい。そうした場合、カードを２枚引く。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニが中央のシグニゾーンにある場合、対戦相手のシグニ１体を対象とし、%W %W %Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "Yukime//Dissona, Blessed General");
        setDescription("en",
                "@U: At the beginning of your main phase, you may put this SIGNI on your field into its owner's trash. If you do, draw two cards.\n@U: At the beginning of your attack phase, if this SIGNI is in your center SIGNI Zone, you may pay %W %W %X. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Yukime//Dissona, Holy General");
        setDescription("en_fan",
                "@U: At the beginning of your main phase, you may put this SIGNI from your field into the trash. If you do, draw 2 cards.\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is in your center SIGNI zone, target 1 of your opponent's SIGNI and you may pay %W %W %X. If you do, return it to their hand."
        );

		setName("zh_simplified", "圣将 雪芽//失调");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，可以把这只精灵从场上放置到废弃区。这样做的场合，抽2张牌。\n" +
                "@U :你的攻击阶段开始时，这只精灵在中央的精灵区的场合，对战对手的精灵1只作为对象，可以支付%W %W%X。这样做的场合，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && trash(getCardIndex()))
            {
                draw(2);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getCardIndex().getLocation() == CardLocation.SIGNI_CENTER)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1)))
                {
                    addToHand(target);
                }
            }
        }
    }
}
