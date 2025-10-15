package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_CodeArtYAkisobaFryer extends Card {

    public SIGNI_R2_CodeArtYAkisobaFryer()
    {
        setImageSets("WX25-P2-076");

        setOriginalName("コードアート　Yキソバキ");
        setAltNames("コードアートワイキソバキ Koodo Aato Wai kisobaki Yakisoba");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のエナゾーンにカードが２枚以上ある場合、あなたのエナゾーンから＜電機＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、対戦相手は自分のエナゾーンからカード１枚を選んでトラッシュに置く。\n" +
                "$$2このシグニが覚醒状態で対戦相手のエナゾーンにカードが３枚以上ある場合、対戦相手は自分のエナゾーンからカードを２枚選びトラッシュに置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Art Y Akisoba Fryer");
        setDescription("en",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there are 2 or more cards in your opponent's ener zone, you may put 1 <<Electric Machine>> SIGNI from your ener zone into the trash. If you do, your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "$$2 If this SIGNI is awakened and there are 3 or more cards in your opponent's ener zone, your opponent chooses 2 cards from their ener zone and puts them into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "必杀代号 炒面机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 对战对手的能量区的牌在2张以上的场合，可以从你的能量区把<<電機>>精灵1张放置到废弃区。这样做的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "$$2 这只精灵在觉醒状态且对战对手的能量区的牌在3张以上的场合，对战对手从自己的能量区选2张牌放置到废弃区。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            if(playerChoiceMode() == 1)
            {
                if(getEnerCount(getOpponent()) >= 2)
                {
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromEner()).get();
                    
                    if(trash(cardIndex))
                    {
                        CardIndex cardIndexEner = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                        trash(cardIndexEner);
                    }
                }
            } else if(isState(CardStateFlag.AWAKENED) && getEnerCount(getOpponent()) >= 3)
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
