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

public final class SIGNI_R1_CodeArtSAndwichToaster extends Card {

    public SIGNI_R1_CodeArtSAndwichToaster()
    {
        setImageSets("WX25-P2-072");

        setOriginalName("コードアート　Hットサンドメーカー");
        setAltNames("コードアートエイチットサンドメーカー Koodo Aato Eichittosandomeekaa Sandwich Maker");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場に他の＜電機＞のシグニがある場合、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2このシグニが覚醒状態の場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Art S Andwich Toaster");
        setDescription("en",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there is another <<Electric Machine>> SIGNI on your field, target 1 of your opponent's SIGNI with power 2000 or less, and banish it.\n" +
                "$$2 If this SIGNI is awakened, target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "必杀代号 热压三明治机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 你的场上有其他的<<電機>>精灵的场合，对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 这只精灵在觉醒状态的场合，对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(2000);

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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).except(getCardIndex()).getValidTargetsCount() > 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                    banish(target);
                }
            } else if(isState(CardStateFlag.AWAKENED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                banish(target);
            }
        }
    }
}
