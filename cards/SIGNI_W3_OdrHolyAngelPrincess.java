package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_OdrHolyAngelPrincess extends Card {

    public SIGNI_W3_OdrHolyAngelPrincess()
    {
        setImageSets("WX25-P2-053");

        setOriginalName("聖天姫　オーズ");
        setAltNames("セイテンキオーズ Seitenki Oozu Odr, ");
        setDescription("jp",
                "@U：あなたの他の白のシグニは対戦相手の効果によって能力を失わない。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの他のシグニ１体を対象とし、%Wを支払ってもよい。そうした場合、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。@@を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Óðr, Holy Angel Princess");
        setDescription("en",
                "@C: Your other white SIGNI can't lose their abilities by your opponent's effects.\n" +
                "@U: At the beginning of your attack phase, target 1 of your other SIGNI, and you may pay %W. If you do, until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "圣天姬 奥兹");
        setDescription("zh_simplified", 
                "@C :你的其他的白色的精灵不会因为对战对手的效果把能力失去。\n" +
                "@U :你的攻击阶段开始时，你的其他的精灵1只作为对象，可以支付%W。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withColor(CardColor.WHITE).except(cardId),
                new RuleCheckModifier<>(CardRuleCheckType.CAN_ABILITY_BE_DISABLED, this::onConstEffModRuleCheck)
            );

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().except(getCardIndex())).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().addToHand(target);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
