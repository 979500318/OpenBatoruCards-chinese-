package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_SchoolLunchPromotionalVehicle extends Card {

    public SIGNI_K1_SchoolLunchPromotionalVehicle()
    {
        setImageSets("WX25-CP1-TK2A");

        setOriginalName("給食推進車両");
        setAltNames("キュウショクスイシンシャリョウ Kyuushoku Suishin Sharyou");
        setDescription("jp",
                "@C：これの上にある《鰐渕アカリ(正月)》のレベルを＋１し、パワーを＋10000する。\n" +
                "@C：これの上にある《鰐渕アカリ(正月)》は@>@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。@@を得る。"
        );

        setDescription("en",
                "@C: The \"Wanibuchi Akari (New Year's)\" on top of this gets +1 level and +10000 power.\n" +
                "@C: The \"Wanibuchi Akari (New Year's)\" on top of this gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --5000 power"
        );

        setName("en", "School Lunch Promotional Vehicle");
        setDescription("en_fan",
                "@C: The \"Akari Wanibuchi (New Year's)\" on top of this gets +1 level and +10000 power.\n" +
                "@C: The \"Akari Wanibuchi (New Year's)\" on top of this gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --5000 power"
        );

		setName("zh_simplified", "供餐推进车辆");
        setDescription("zh_simplified", 
                "@C :此牌上面的《鰐渕アカリ(正月)》的等级+1，力量+10000。\n" +
                "@C :此牌上面的《鰐渕アカリ(正月)》得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-5000。@@\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ConstantAbility cont1 = registerConstantAbility(new TargetFilter().own().SIGNI().withName("鰐渕アカリ(正月)").over(cardId),
                new ModifiableValueModifier<>(cardIndex -> cardIndex.getIndexedInstance().getLevel(), () -> 1),
                new PowerModifier(10000)
            );
            cont1.setActiveUnderFlags(CardUnderCategory.UNDER);

            ConstantAbility cont2 = registerConstantAbility(new TargetFilter().own().SIGNI().withName("鰐渕アカリ(正月)").over(cardId), new AbilityGainModifier(this::onConstEff2ModGetSample));
            cont2.setActiveUnderFlags(CardUnderCategory.UNDER);
        }

        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);

            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
