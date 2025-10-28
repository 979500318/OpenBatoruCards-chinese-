package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckCanPayEner;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class ARTS_R_WithAllGunsBlazing extends Card {

    public ARTS_R_WithAllGunsBlazing()
    {
        setImageSets("WX25-P2-004", "WX25-P2-004U");

        setOriginalName("全砲全開");
        setAltNames("ゼンホウゼンカイ Zenhou Zenkai");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキからアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%X %X %X減る。\n\n" +
                "ターン終了時まで、対戦相手のすべてのシグニは@>@U：このシグニがアタックしたとき、%X %X %Xを支払わなければ、このシグニをバニッシュする。@@を得る。\n" +
                "&E４枚以上@0追加で、ターン終了時まで、あなたは@>@C $TP：シグニアタックステップの間、対戦相手は１以上のエナコストを支払えない。@@を得る。"
        );

        setName("en", "With All Guns Blazing");
        setDescription("en",
                "While using this ARTS, you may put 1 ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %X %X %X.\n\n" +
                "Until end of turn, all of your opponent's SIGNI gain:" +
                "@>@U: Whenever this SIGNI attacks, banish it unless you pay %X %X %X.@@" +
                "&E4 or more@0 Additionally, until end of turn, you gain:" +
                "@>@C $TP: During the SIGNI attack step, your opponent can't pay ener costs of 1 or more."
        );

        setName("zh_simplified", "全炮全开");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%X %X %X。\n" +
                "直到回合结束时为止，对战对手的全部的精灵得到" +
                "@>@U :当这只精灵攻击时，如果不把%X %X %X支付，那么这只精灵破坏。@@" +
                "&E4张以上@0追加，直到回合结束时为止，你得到" +
                "@>@C $TP :精灵攻击步骤期间，对战对手不能把1以上的能量费用支付。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(5));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().except(cardId).fromLocation(CardLocation.DECK_LRIG)), Cost.colorless(3));
            arts.setRecollect(4);
        }

        private void onARTSEff()
        {
            forEachSIGNIOnField(getOpponent(), cardIndex -> {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
            });
            
            if(getAbility().isRecollectFulfilled())
            {
                ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_PAY_ENER, TargetFilter.HINT_OWNER_OP, this::onAttachedConstEffModRuleCheck));
                attachedConst.setCondition(this::onConstEffCond);
                attachedConst.setNestedDescriptionOffset(1);
                
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = getAbility().getSourceCardIndex();
            if(!cardIndex.getIndexedInstance().payEner(Cost.colorless(3)))
            {
                cardIndex.getIndexedInstance().banish(cardIndex);
            }
        }
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_SIGNI ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return !RuleCheckCanPayEner.getDataCostString(data).isEmpty() ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
