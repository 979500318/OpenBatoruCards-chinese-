package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_K3_BellCricketGreatPhantomInsect extends Card {

    public SIGNI_K3_BellCricketGreatPhantomInsect()
    {
        setImageSets("WX24-P1-050", Mask.IGNORE+"SPDi10-19");

        setOriginalName("大幻蟲　ベル・クリケット");
        setAltNames("ダイゲンチュウベルクリケット Daigenchuu Beru Kuriketto");
        setDescription("jp",
                "@C：アタックフェイズの間、このシグニの正面のシグニのパワーを－2000する。\n" +
                "@U：各アタックフェイズ開始時、ターン終了時まで、対戦相手のパワー10000以下のすべてのシグニは能力を失う。" +
                "~#：どちらか１つを選ぶ。\n$$1対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。\n$$2カードを１枚引く。"
        );

        setName("en", "Bell Cricket, Great Phantom Insect");
        setDescription("en",
                "@C: During the attack phase, the SIGNI in front of this SIGNI gets --2000 power.\n" +
                "@U: At the beginning of each attack phase, until end of turn, all of your opponent's SIGNI with power 10000 or less lose their abilities." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "大幻虫 铃·虫");
        setDescription("zh_simplified", 
                "@C :攻击阶段期间，这只精灵的正面的精灵的力量-2000。\n" +
                "@U :各攻击阶段开始时，直到回合结束时为止，对战对手的力量10000以下的全部的精灵的能力失去。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new PowerModifier(-2000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && cardIndex == getOppositeSIGNI() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            disableAllAbilities(new TargetFilter().OP().SIGNI().withPower(0,10000).getExportedData(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            } else {
                draw(1);
            }
        }
    }
}
