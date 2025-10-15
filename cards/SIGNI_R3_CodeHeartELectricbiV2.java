package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_CodeHeartELectricbiV2 extends Card {

    public SIGNI_R3_CodeHeartELectricbiV2()
    {
        setImageSets("WX25-P2-054", "WX25-P2-054U");
        setLinkedImageSets("WX25-P2-018");

        setOriginalName("コードハート　DンドウバV2");
        setAltNames("コードハートディーンドウバヴィツー Koodo Haato Diindouba Vitsuu Electricbi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《ララ・ルー\"Third\"》がいる場合、対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。このシグニが覚醒状態の場合、代わりに対戦相手のパワー13000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に他の＜電機＞のシグニがあり対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );

        setName("en", "Code Heart E Lectricbi V2");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Lalaru \"Third\"\", target 1 of your opponent's SIGNI with power 5000 or less, and banish it. If this SIGNI is awakened, instead target 1 of your opponent's SIGNI with power 13000 or less, and banish it.\n" +
                "@U: Whenever this SIGNI attacks, if there is another <<Electric Machine>> SIGNI on your field and there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash."
        );

		setName("zh_simplified", "爱心代号 电动马V2");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《ララ・ルー\"Third\"》的场合，对战对手的力量5000以下的精灵1只作为对象，将其破坏。这只精灵在觉醒状态的场合，作为替代，对战对手的力量13000以下的精灵1只作为对象，将其破坏。\n" +
                "@U :当这只精灵攻击时，你的场上有其他的<<電機>>精灵且对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ララ・ルー\"Third\""))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, !isState(CardStateFlag.AWAKENED) ? 5000 : 13000)).get();
                banish(target);
            }
        }
        
        private void onAutoEff2()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).except(getCardIndex()).getValidTargetsCount() > 0 &&
               getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
    }
}
