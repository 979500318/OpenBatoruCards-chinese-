package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R3_DynamiteTreeNaturalPlantPrincess extends Card {

    public SIGNI_R3_DynamiteTreeNaturalPlantPrincess()
    {
        setImageSets("WX25-P1-055");

        setOriginalName("羅植姫　ダイナマイトツリー");
        setAltNames("ラショクヒメダイナマイトツリー Rashokuhime Dainamaito Tsurii");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー8000以下のシグニ１体を対象とし、アップ状態のこのシグニをダウンしてもよい。そうした場合、それをバニッシュする。\n" +
                "@U：あなたのターン終了時、対戦相手のエナゾーンから無色のカード１枚を対象とし、それをトラッシュに置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Dynamite Tree, Natural Plant Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 8000 or less, and you may down this upped SIGNI. If you do, banish it.\n" +
                "@U: At the end of your turn, target 1 colorless card from your opponent's ener zone, and put it into the trash." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "罗植姬 炸药树");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量8000以下的精灵1只作为对象，可以把竖直状态的这只精灵横置。这样做的场合，将其破坏。\n" +
                "@U :你的回合结束时，从对战对手的能量区把无色的牌1张作为对象，将其放置到废弃区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(3);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && !isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down(getCardIndex()))
            {
                banish(target);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor())).get();
            trash(target);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
