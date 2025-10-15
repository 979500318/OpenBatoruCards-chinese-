package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition;

public final class SIGNI_R1_YuzukiMemoriaPhantomDragon extends Card {

    public SIGNI_R1_YuzukiMemoriaPhantomDragon()
    {
        setImageSets("WXDi-P08-057", "WXDi-P08-057P", "SPDi01-78","SPDi38-14");

        setOriginalName("幻竜　遊月//メモリア");
        setAltNames("ゲンリュウユヅキメモリア Genryuu Yudzuki Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが４枚以上ある場合、%R %X %Xを支払ってもよい。そうした場合、対戦相手は自分のエナゾーンからカードを２枚選びトラッシュに置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Yuzuki//Memoria, Phantom Dragon");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are four or more cards in your opponent's Ener Zone, you may pay %R %X %X. If you do, your opponent chooses two cards from their Ener Zone and puts them into their trash." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Yuzuki//Memoria, Phantom Dragon");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 4 or more cards in your opponent's ener zone, you may pay %R %X %X. If you do, your opponent chooses 2 cards from their ener zone and puts them into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "幻龙 游月//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在4张以上的场合，可以支付%R%X %X。这样做的场合，对战对手从自己的能量区选2张牌放置到废弃区。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardConst.CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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

            AutoAbility auto = registerAutoAbility(GameConst.GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private AbilityCondition.ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GameConst.GamePhase.ATTACK_PRE ? AbilityCondition.ConditionState.OK : AbilityCondition.ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 4 && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(2)))
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), 2, new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
