package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_AllosaurusPhantomDragon extends Card {

    public SIGNI_R2_AllosaurusPhantomDragon()
    {
        setImageSets("WX24-P4-066");

        setOriginalName("幻竜　アロサウルス");
        setAltNames("ゲンリュウアロサウルス Genryuu Arosaurusu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが２枚以上ある場合、%Xを支払ってもよい。そうした場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Allosaurus, Phantom Dragon");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are 2 or more cards in your opponent's ener zone, you may pay %X. If you do, your opponent chooses 1 card from their ener zone, and puts it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "幻龙 异特龙");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在2张以上的场合，可以支付%X。这样做的场合，对战对手从自己的能量区选1张牌放置到废弃区。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(2);
        setPower(8000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2 && payEner(Cost.colorless(1)))
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
