package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class LRIG_G3_ShirokoSunaookamiDroneFireSupport extends Card {

    public LRIG_G3_ShirokoSunaookamiDroneFireSupport()
    {
        setImageSets("WXDi-CP02-009");

        setOriginalName("砂狼シロコ[ドローン召喚：火力支援]");
        setAltNames("スナオオカミシロコドローンショウカンカリョクシエン Sunaookami Shiroko Doroon Shoukan Karyokushien");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたのエナゾーンにカードが２枚以上置かれていた場合、あなたのシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、ターン終了時まで、それは【Ｓランサー】を得る。\n" +
                "@A $G1 %G0：【エナチャージ３】をする。その後、あなたのエナゾーンからカードを１枚まで対象とし、それを手札に加える。" +
                "~{{A $T1 %X：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Sunaookami Shiroko [Drone Support]");
        setDescription("en",
                "@U: At the beginning of your attack phase, if two or more cards were put into your Ener Zone this turn, you may pay %G %X. If you do, target SIGNI on your field gains [[S Lancer]] until end of turn.\n@A $G1 %G0: [[Ener Charge 3]]. Then, add up to one target card from your Ener Zone to your hand.~{{A $T1 %X: Add target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Shiroko Sunaookami [Drone: Fire Support]");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if 2 or more cards were put into your ener zone this turn, target 1 of your SIGNI, and you may pay %G %X. If you do, until end of turn, it gains [[S Lancer]].\n" +
                "@A $G1 %G0: [[Ener Charge 3]]. Then, target up to 1 card from your ener zone, and add it to your hand." +
                "~{{A $T1 %X: Target 1 SIGNI from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "砂狼白子[召唤无人机：火力支援]");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你的能量区有牌2张以上放置的场合，你的精灵1只作为对象，可以支付%G%X。这样做的场合，直到回合结束时为止，其得到[[S枪兵]]。\n" +
                "@A $G1 %G0:[[能量填充3]]。然后，从你的能量区把牌1张最多作为对象，将其加入手牌。\n" +
                "~{{A$T1 %X:从你的能量区把精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHIROKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ENER && isOwnCard(event.getCaller())) >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();

                if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                {
                    attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
                }
            }
        }

        private void onActionEff1()
        {
            enerCharge(3);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromEner()).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
