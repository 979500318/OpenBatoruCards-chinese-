package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_AdamHolyAngelPrincess extends Card {

    public SIGNI_W3_AdamHolyAngelPrincess()
    {
        setImageSets("WX24-P2-047");
        setLinkedImageSets("WX24-P2-014");

        setOriginalName("聖天姫　アダム");
        setAltNames("セイテンキアダム Seitenki Adamu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《満月の使徒　小湊るう子》がいる場合、%Wを支払ってもよい。そうした場合、以下の２つから１つを選ぶ。\n" +
                "$$1このターン、対戦相手は追加で%X %Xを支払わないかぎり【ガード】ができない。\n" +
                "$$2対戦相手のルリグ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@C：%X %Xを支払わないかぎりアタックできない。@@を得る。\n" +
                "@A #D：あなたの場に＜悪魔＞のシグニがある場合、カードを１枚引く。"
        );

        setName("en", "Adam, Holy Angel Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Ruuko Kominato, Full Moon Apostle\", you may pay %W. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 This turn, your opponent can't [[Guard]], unless they pay %X %X.\n" +
                "$$2 Target 1 of your opponent's LRIG. Until the end of your opponent's next turn, it gains:" +
                "@>@C: Can't attack unless you pay %X %X.@@" +
                "@A #D: If there is a <<Devil>> SIGNI on your field, draw 1 card."
        );

		setName("zh_simplified", "圣天姬 亚当");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《満月の使徒　小湊るう子》的场合，可以支付%W。这样做的场合，从以下的2种选1种。\n" +
                "$$1 这个回合，对战对手如果不追加把%X %X:支付，那么不能[[防御]]。\n" +
                "$$2 对战对手的分身1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C 如果不把%X %X:支付，那么不能攻击。@@\n" +
                "@A #D:你的场上有<<悪魔>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }

        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("満月の使徒　小湊るう子") &&
                payEner(Cost.color(CardColor.WHITE, 1)))
            {
                if(playerChoiceMode() == 1)
                {
                    addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_GUARD, getOpponent(), ChronoDuration.turnEnd(), data -> new EnerCost(Cost.colorless(2)));
                } else {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(2))));
                    attachAbility(target, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
        
        private void onActionEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
    }
}
