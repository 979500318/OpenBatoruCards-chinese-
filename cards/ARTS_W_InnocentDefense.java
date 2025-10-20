package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_InnocentDefense extends Card {

    public ARTS_W_InnocentDefense()
    {
        setImageSets("WX16-006", "WXK01-006");

        setOriginalName("イノセント・ディフェンス");
        setAltNames("イノセントディフェンス Inosento Difensu");
        setDescription("jp",
                "このアーツの使用コストに含まれるコストは、あなたのセンタールリグが持つ色でしか支払えない。\n\n" +
                "以下の４つから２つまで選ぶ。\n" +
                "$$1対戦相手のセンタールリグ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "$$2対戦相手のシグニ１体を対象とし、それをダウンし凍結する。\n" +
                "$$3あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@C：バニッシュされない。@@を得る。\n" +
                "$$4あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Innocent Defense");
        setDescription("en",
                "((The %X in the use cost of this ARTS can only be paid with the colors of your center LRIG))\n\n" +
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Target your opponent's center LRIG, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "$$2 Target 1 of your opponent's SIGNI, and down and freeze it.\n" +
                "$$3 Target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't be banished.@@" +
                "$$4 Target up to 2 SIGNI that share a common color with your center LRIG from your trash, and add them to your hand."
        );

		setName("zh_simplified", "纯真·防御");
        setDescription("zh_simplified", 
                "这张必杀的使用费用含有的无色费用，只能用你的核心分身的持有颜色支付。\n" +
                "从以下的4种选2种最多。\n" +
                "$$1 对战对手的核心分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "$$2 对战对手的精灵1只作为对象，将其横置并冻结。\n" +
                "$$3 你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不会被破坏。@@\n" +
                "$$4 从你的废弃区把持有与你的核心分身共通颜色的精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.ARTS);
        setCost(Cost.colorless(4));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(0,2);
            arts.setOnAbilityInit(() -> ((EnerCost)arts.getCost()).setColorPayFilter(color ->
                color == CardColor.COLORLESS ? getLRIG(arts.getAbilityOwner()).getIndexedInstance().getColor() : null
            ));
        }
        
        private void onARTSEff()
        {
            int modes = arts.getChosenModes();
            
            if((modes & 1<<0) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().LRIG()).get();
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                down(target);
                freeze(target);
            }
            if((modes & 1<<2) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, data -> RuleCheckState.BLOCK));
                    attachedConst.setNestedDescriptionOffset(1);
                    attachAbility(target, attachedConst, ChronoDuration.turnEnd());
                }
            }
            if((modes & 1<<3) != 0)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash());
                addToHand(data);
            }
        }
    }
}
