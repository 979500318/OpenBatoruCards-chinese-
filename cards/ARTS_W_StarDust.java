package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;
import open.batoru.game.gfx.GFXFieldBackground;

public final class ARTS_W_StarDust extends Card {

    public ARTS_W_StarDust()
    {
        setImageSets("WX25-P2-001", "WX25-P2-001U");

        setOriginalName("スター・ダスト");
        setAltNames("スターダスト Sutaa Dasuto");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：対戦相手は追加で無を支払わないかぎり【ガード】ができない。\n" +
                "@A $T1 @[手札から#Gを持つシグニを１枚捨てる]@：【ルリグバリア】１つを得る。"
        );

        setName("en", "Star Dust");
        setDescription("en",
                "During this game, you gain the following abilities:" +
                "@>@C: Your opponent can't [[Guard]] unless they pay %X.\n" +
                "@A $T1 @[Discard 1 #G @[Guard]@ SIGNI from your hand]@: Gain 1 [[LRIG Barrier]]."
        );

		setName("zh_simplified", "星光·除灰");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@C 对战对手如果不追加把%X:支付，那么不能[[防御]]。\n" +
                "@A $T1 从手牌把持有#G的精灵1张舍弃得到[[分身屏障]]1个。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

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
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD,
                TargetFilter.HINT_OWNER_OP, data -> new EnerCost(Cost.colorless(1)))
            );
            GFXFieldBackground.attachToAbility(attachedConst, new GFXFieldBackground(getOwner(), CardLocation.LRIG, "aura_stars"));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());

            ActionAbility attachedAct = new ActionAbility(new DiscardCost(new TargetFilter().SIGNI().guard()), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachPlayerAbility(getOwner(), attachedAct, ChronoDuration.permanent());
        }
        private void onAttachedActionEff()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
        }
    }
}

