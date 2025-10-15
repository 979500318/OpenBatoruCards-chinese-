package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_CodeRideLaylaTHEDOOR extends Card {

    public SIGNI_G1_CodeRideLaylaTHEDOOR()
    {
        setImageSets("WXDi-P15-069");

        setOriginalName("コードライド　レイラ//THE DOOR");
        setAltNames("コードライドレイラザドアー Koodo Raida Reira Za Doaa");
        setDescription("jp",
                "@U $T2：あなたが#Cを１枚以上支払ったとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋2000する。\n" +
                "@A #C #C：ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニのパワーが5000以下であるかぎり、【ランサー】\n@C：このシグニは各ターンに一度しかアタックできない。@@を得る。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Layla//THE DOOR, Code: Ride");
        setDescription("en",
                "@U $T2: Whenever you pay one or more #C, this SIGNI gets +2000 power until the end of your opponent's next end phase.\n@A #C #C: This SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI has power 5000 or less, this SIGNI gains [[Lancer]].\n@C: This SIGNI can only attack once each turn.@@until end of turn." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Code Ride Layla//THE DOOR");
        setDescription("en_fan",
                "@U $T2: When you pay 1 or more #C, until the end of your opponent's next turn, this SIGNI gets +2000 power.\n" +
                "@A #C #C: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 5000 or less, this SIGNI gains [[Lancer]].\n" +
                "@C: This SIGNI can only attack once per turn.@@" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "骑乘代号 蕾拉//THE DOOR");
        setDescription("zh_simplified", 
                "@U $T2 当你把币:1个以上支付时，直到下一个对战对手的回合结束时为止，这只精灵的力量+2000。\n" +
                "@A #C #C:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在5000以下时，得到[[枪兵]]。@@\n" +
                "@>@C :这只精灵在各回合只能攻击一次。@@" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.COIN, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);

            registerActionAbility(new CoinCost(2), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnCard(getEvent().getSource()) && EventCoin.getDataGainedCoins() < 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onActionEff()
        {
            ConstantAbility attachedConst1 = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEff1ModGetSample));
            attachedConst1.setCondition(this::onAttachedConstEff1Cond);
            attachAbility(getCardIndex(), attachedConst1, ChronoDuration.turnEnd());
            
            ConstantAbility attachedConst2 = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ATTACK, this::onAttachedConstEff2ModRuleCheck));
            attachedConst2.setNestedDescriptionOffset(1);
            attachAbility(getCardIndex(), attachedConst2, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEff1Cond()
        {
            return getOppositeSIGNI() != null && getOppositeSIGNI().getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEff1ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
        private RuleCheckState onAttachedConstEff2ModRuleCheck(RuleCheckData data)
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && event.getCallerCardIndex() == getCardIndex()) > 0 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
