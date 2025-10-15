package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_K3_CodeAccelMDragon extends Card {

    public SIGNI_K3_CodeAccelMDragon()
    {
        setImageSets("SPDi43-10");
        setLinkedImageSets("SPDi43-05");

        setOriginalName("コードアクセル　Ｍドラゴン");
        setAltNames("コードアクセルマキナドラゴン Koodo Akuseru Makina Doragon");
        setDescription("jp",
                "@C：このシグニは【ソウル】が付いているかぎり、[[シャドウ（コストの合計が１以下のアーツ）]]を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に《マキナ・スリーNEO》がいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のデッキの上からカードを６枚トラッシュに置く。\n" +
                "$$2%Kを支払ってもよい。そうした場合、このターン、次にあなたのルリグがアタックしたとき、そのアタックの間、対戦相手のライフクロスが０枚であるかぎり、対戦相手は【ガード】ができない。"
        );

        setName("en", "Code Accel M Dragon");
        setDescription("en",
                "@C: As long as this SIGNI has a [[Soul]] attached to it, this SIGNI gains [[Shadow (ARTS with a total cost of 1 or less)]].\n" +
                "@U: At the beginning of your attack phase, if your LRIG is \"Machina Three NEO\", @[@|choose 1 of the following:|@]@\n" +
                "$$1 Put the top 6 cards of your opponent's deck into the trash.\n" +
                "$$2 You may pay %K. If you do, this turn, the next time your LRIG attacks, as long as your opponent has 0 life cloth, your opponent can't [[Guard]] during that attack."
        );

		setName("zh_simplified", "加速代号 M多拉贡");
        setDescription("zh_simplified", 
                "@C :这只精灵有[[灵魂]]附加时，得到[[暗影（费用的合计在1以下的必杀）]]。\n" +
                "@U :你的攻击阶段开始时，你的场上有《マキナ・スリーNEO》的场合，从以下的2种选1种。\n" +
                "$$1 从对战对手的牌组上面把6张牌放置到废弃区。\n" +
                "$$2 可以支付%K。这样做的场合，这个回合，当下一次你的分身攻击时，那次攻击期间，如果对战对手的生命护甲在0张，那么对战对手不能[[防御]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
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

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return getCardIndex().getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_SOUL) > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getCardReference().getType() == CardType.ARTS &&
                   Cost.getOriginalCostAsNumber(cardIndexSource.getCardReference()) <= 1 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("マキナ・スリーNEO"))
            {
                if(playerChoiceMode() == 1)
                {
                    millDeck(getOpponent(), 6);
                } else {
                    if(payEner(Cost.color(CardColor.BLACK, 1)))
                    {
                        int countAttacks = getLRIGAttacksCount();
                        
                        ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                        
                        ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_GUARD, TargetFilter.HINT_OWNER_OP, data -> {
                            record.forceExpire();
                            return RuleCheckState.BLOCK;
                        }));
                        attachedConst.setCondition(() -> getLRIGAttacksCount() <= countAttacks+1 && getLifeClothCount(getOpponent()) == 0 ? ConditionState.OK : ConditionState.BAD);
                        
                        attachPlayerAbility(getOwner(), attachedConst, record);
                    }
                }
            }
        }
        private int getLRIGAttacksCount()
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && CardType.isLRIG(event.getCaller().getCardReference().getType()) && !isOwnCard(event.getSource()));
        }
    }
}
