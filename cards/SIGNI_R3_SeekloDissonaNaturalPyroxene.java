package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_SeekloDissonaNaturalPyroxene extends Card {

    public SIGNI_R3_SeekloDissonaNaturalPyroxene()
    {
        setImageSets("WXDi-P12-047");
        setLinkedImageSets("WXDi-P12-007");

        setOriginalName("羅輝石　シークラ//ディソナ");
        setAltNames("ラキセキシークラディソナ Rakiseki Shiikura Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが#Sで対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@U：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、あなたの場に《炎妖舞　花代・惨》がいる場合、対戦相手が%Xを支払わないかぎり、そのカードのライフバーストは発動しない。" +
                "~#：カードを２枚引き【エナチャージ１】をする。"
        );

        setName("en", "SeekLove//Dissona, Crystal Brilliance");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are #S and there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n@U: Whenever this SIGNI crushes one of your opponent's Life Cloth, if \"Hanayo Tragic, Flame Dance\" is on your field, the crushed card's Life Burst does not activate unless your opponent pays %X." +
                "~#Draw two cards and [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Seeklo//Dissona, Natural Pyroxene");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are #S @[Dissona]@ SIGNI and there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "@U: Whenever this SIGNI crushes 1 of your opponent's life cloth, if your LRIG is \"Wretched Hanayo, Bewitching Flame Dance\", that card's ## @[Life Burst]@ doesn't activate unless your opponent pays %X." +
                "~#Draw 2 cards, and [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗辉石 相思//失调");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的全部的精灵是#S且对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@U 当这只精灵把对战对手的生命护甲1张击溃时，你的场上有《炎妖舞　花代・惨》的场合，如果对战对手不把%X:支付，那么那张牌的生命迸发不能发动。" +
                "~#抽2张牌并[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            AutoAbility auto2 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2 &&
                    new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("炎妖舞　花代・惨"))
            {
                addCardRuleCheck(CardRuleCheckType.COST_TO_USE_LB, caller, ChronoDuration.nextPhase(), data -> new EnerCost(Cost.colorless(1)));
            }
        }

        private void onLifeBurstEff()
        {
            draw(2);
            enerCharge(1);
        }
    }
}

