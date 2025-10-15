package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G3_FlowerStandNaturalPlantPrincess extends Card {

    public SIGNI_G3_FlowerStandNaturalPlantPrincess()
    {
        setImageSets("WX24-P3-056");

        setOriginalName("羅植姫　フラスタ");
        setAltNames("ラショクヒメフラスタ Rashokuhime Hurasuta");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンにあるカードが４枚以下の場合、【エナチャージ１】をする。\n" +
                "@E：あなたのエナゾーンからシグニを１枚まで対象とし、それを場に出す。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"

        );

        setName("en", "Flower Stand, Natural Plant Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are four or less cards in your ener zone, [[Ener Charge 1]].\n" +
                "@E: Target up to 1 SIGNI from your ener zone, and put it onto your field." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "罗植姬 后援花篮");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的能量区的牌在4张以下的场合，[[能量填充1]]。\n" +
                "@E :从你的能量区把精灵1张最多作为对象，将其出场。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOwner()) <= 4)
            {
                enerCharge(1);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
