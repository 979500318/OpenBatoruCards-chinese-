package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.AbilityORCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.gfx.GFXFieldBackground;

public final class ARTS_G_OneFormDividedIntoMany extends Card {

    public ARTS_G_OneFormDividedIntoMany()
    {
        setImageSets("WX25-P2-007", "WX25-P2-007U");

        setOriginalName("一体分身");
        setAltNames("シルクハットマジック Siruku Hatto Majikku Silk Hat Magic");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：あなたが【ガード】する際、#Gを持つカードを１枚捨てる代わりにあなたのエナゾーンからカード１枚と#Gを持つカード１枚をトラッシュに置いてもよい。\n" +
                "@U：あなたのエナフェイズ開始時、【エナチャージ１】をする。"
        );

        setName("en", "One Form Divided into Many");
        setDescription("en",
                "During this game, you gain the following abilities:" +
                "@>@C: You may [[Guard]] by putting 1 card with #G @[Guard]@ and 1 SIGNI each from your ener zone into the trash instead of discarding 1 card with #G @[Guard]@.\n" +
                "@U: At the beginning of your ener phase, [[Ener Charge 1]]."
        );

        setName("zh_simplified", "一体分身");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。" +
                "@>@C :你[[防御]]时，把持有#G的牌1张舍弃，作为替代，可以从你的能量区把1张牌和持有#G的牌1张放置到废弃区。\n" +
                "@U :你的充能阶段开始时，[[能量填充1]]。（从手牌或场上往能量区把牌放置前[[能量填充1]]）@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
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
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OWN, data ->
                new AbilityORCost(AbilityORCost.REPLACE_DEFAULT, new TrashCost(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().and(new TargetFilter().guard(), new TargetFilter().SIGNI().fromEner()).fromEner()))
            ));
            GFXFieldBackground.attachToAbility(attachedConst, new GFXFieldBackground(getOwner(), CardLocation.LRIG, "aura_magic"));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());

            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setNestedDescriptionOffset(1);
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ENER ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}

