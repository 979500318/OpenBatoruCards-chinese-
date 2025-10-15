package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckMustSkipPhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_R2_HanayoTwoAlteredRoaringFlame extends Card {

    public LRIG_R2_HanayoTwoAlteredRoaringFlame()
    {
        setImageSets("SP38-008");

        setOriginalName("轟炎　花代・爾転");
        setAltNames("ゴウエンハナヨニテン Gouen Hanayo Niten");
        setDescription("jp",
                "@C：あなたのグロウフェイズをスキップする。\n" +
                "@C $TO：あなたの中央のシグニゾーンにあるシグニのパワーを＋5000する。\n" +
                "@A $G1 %R %R %R：このターン、次にこのルリグがアタックしたとき、そのアタックの間、対戦相手は【ガード】ができない。"
        );

        setName("en", "Hanayo Two Altered, Roaring Flame");
        setDescription("en",
                "@C: Skip your grow phase.\n" +
                "@C $TO: The SIGNI in your center SIGNI zone gets +5000 power.\n" +
                "@A $G1 %R %R %R: This turn, the next time this LRIG attacks, your opponent can't [[Guard]] during that attack."
        );

		setName("zh_simplified", "轰炎 花代·贰转");
        setDescription("zh_simplified", 
                "@C :你的成长阶段跳过。\n" +
                "@C $TO :你的中央的精灵区的精灵的力量+5000。\n" +
                "@A $G1 %R %R %R:这个回合，当下一次这只分身攻击时，那次攻击期间，对战对手不能[[防御]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(2);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.MUST_SKIP_PHASE, TargetFilter.HINT_OWNER_OWN, this::onConstEff1ModRuleCheck));
            
            registerConstantAbility(this::onConstEff2Cond, new TargetFilter().own().SIGNI().fromLocation(CardLocation.SIGNI_CENTER), new PowerModifier(5000));
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 3)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private RuleCheckState onConstEff1ModRuleCheck(RuleCheckData data)
        {
            return RuleCheckMustSkipPhase.getDataGamePhase(data) == GamePhase.GROW ? RuleCheckState.OK : RuleCheckState.IGNORE;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_GUARD, getOpponent(), record, data -> {
                record.forceExpire();
                return RuleCheckState.BLOCK;
            });
        }
    }
}
