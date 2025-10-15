package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class ARTS_WR_FriendshipFlame extends Card {

    public ARTS_WR_FriendshipFlame()
    {
        setImageSets("WX24-P4-001", "WX24-P4-001U");

        setOriginalName("フレンドシップ・フレイム");
        setAltNames("フレンドシップフレイム Furendoshippu Fureimu");
        setDescription("jp",
                "対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C：%X %X %X %Xを支払わないかぎりアタックできない。@@を得る。対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、%X %X %X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。"
        );

        setName("en", "Friendship Flame");
        setDescription("en",
                "Target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C: Can't attack unless you pay %X %X %X %X.@@" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, banish this SIGNI unless you pay %X %X %X %X."
        );

		setName("zh_simplified", "友谊·之火");
        setDescription("zh_simplified", 
                "对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C 如果不把%X %X %X %X:支付，那么不能攻击。@@\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X %X %X %X:支付，那么这只精灵破坏。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE, CardColor.RED);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.color(CardColor.RED, 1));
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
            
            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(4))));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
            
            target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setNestedDescriptionOffset(1);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            if(!source.getIndexedInstance().payEner(Cost.colorless(4)))
            {
                source.getIndexedInstance().banish(source);
            }
        }
    }
}
