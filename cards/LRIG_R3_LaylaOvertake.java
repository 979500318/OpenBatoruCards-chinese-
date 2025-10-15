package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.DownCost;

public final class LRIG_R3_LaylaOvertake extends Card {

    public LRIG_R3_LaylaOvertake()
    {
        setImageSets("WXDi-P09-006", "WXDi-P09-006U");

        setOriginalName("レイラ＝オーバーテイク");
        setAltNames("レイラオーバーテイク Reira Oobaateiku");
        setDescription("jp",
                "@A #D：あなたの赤のシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニがアタックしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。\n" +
                "@A $G1 @[手札をすべて捨てる]@：このターン、次にこのルリグがアタックしたとき、そのアタックの間、対戦相手は【ガード】ができない。"
        );

        setName("en", "Layla =Overtake=");
        setDescription("en",
                "@A #D: Target red SIGNI on your field gains@>@U $T1: When this SIGNI attacks, vanish target SIGNI on your opponent's field with power 8000 or less.@@until end of turn.\n" +
                "@A $G1 @[Discard your hand]@: When this LRIG attacks next this turn, your opponent cannot [[Guard]] during that attack."
        );
        
        setName("en_fan", "Layla-Overtake");
        setDescription("en_fan",
                "@A #D: Target 1 of your red SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI attacks, target 1 of your opponent's SIGNI with power 8000 or less, and banish it.@@" +
                "@A $G1 @[Discard all cards from your hand]@: This turn, the next time this LRIG attacks, during that attack, your opponent can't [[Guard]]."
        );

		setName("zh_simplified", "蕾拉=超车");
        setDescription("zh_simplified", 
                "@A #D:你的红色的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵攻击时，对战对手的力量8000以下的精灵1只作为对象，将其破坏。@@\n" +
                "@A $G1 手牌全部舍弃:这个回合，当下一次这只分身攻击时，那次攻击期间，对战对手不能[[防御]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new DownCost(), this::onActionEff1);
            
            ActionAbility act = registerActionAbility(new DiscardCost(() -> getHandCount(getOwner())), this::onActionEff2);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameConst.GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
        
        private void onActionEff2()
        {
            ChronoRecordScheduler.ChronoRecord record = new ChronoRecordScheduler.ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckRegistry.PlayerRuleCheckType.CAN_GUARD, getOpponent(), record, data -> {
                record.forceExpire();
                return RuleCheck.RuleCheckState.BLOCK;
            });
        }
    }
}
