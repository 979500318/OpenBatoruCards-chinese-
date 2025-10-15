package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.PlayerRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.FieldConst;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXFieldBackground;

public final class LRIG_B3_MiyakoTsukiyukiSelfPropelledFlashDrone extends Card {

    public LRIG_B3_MiyakoTsukiyukiSelfPropelledFlashDrone()
    {
        setImageSets("WX25-CP1-016", "WX25-CP1-016U");

        setOriginalName("月雪ミヤコ[自走式閃光ドローン]");
        setAltNames("ツキユキミヤコジソウシキセンコウドローン Tsukiyuki Miyako Jisoushiki Senkou Doroon");
        setDescription("jp",
                "@U $T1：シグニかスペルの、コストか効果によってあなたが手札を１枚捨てたとき、カードを１枚引く。\n" +
                "@A $G1 %B0：対戦相手のルリグ１体を対象とし、それを凍結する。次の対戦相手のターンの間、対戦相手はルリグの@A能力を使用できない。" +
                "~{{A $G1 %B0：カードを２枚引く。"
        );

        setName("en", "Tsukiyuki Miyako [Self-Propelled Flash Drone]");

        setName("en_fan", "Miyako Tsukiyuki [Self-Propelled Flash Drone]");
        setDescription("en",
                "@U $T1: When you discard 1 card from your hand due to a cost or an effect of a SIGNI or spell, draw 1 card.\n" +
                "@A $G1 %B0: Target 1 of your opponent's LRIG, and freeze it. During your opponent's next turn, your opponent can't use LRIG @A abilities." +
                "~{{A $G1 %B0: Draw 2 cards."
        );

		setName("zh_simplified", "月雪宫子[自行闪光无人机]");
        setDescription("zh_simplified", 
                "@U $T1 :当因为精灵或魔法的，费用或效果你把手牌1张舍弃时，抽1张牌。\n" +
                "@A $G1 %B0对战对手的分身1只作为对象，将其冻结。下一个对战对手的回合期间，对战对手不能把分身的@A能力使用。\n" +
                "~{{A$G1 %B0:抽2张牌。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIYAKO);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
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

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && getEvent().getSourceAbility() != null &&
                   (CardType.isSIGNI(getEvent().getSource().getCardReference().getType()) || getEvent().getSource().getCardReference().getType() == CardType.SPELL) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
            
            ConstantAbility attachedConst = new ConstantAbility(
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_ABILITY, TargetFilter.HINT_OWNER_OP, this::onAttachedConstEffModRuleCheck)
            );
            attachedConst.setCondition(() -> !isOwnTurn() ? ConditionState.OK : ConditionState.BAD);
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
            GFX.attachToChronoRecord(record, new GFXFieldBackground(getOpponent(), "miyako_halo", 1200,1200, FieldConst.FIELD_CARD_WIDTH*2+72,FieldConst.FIELD_ZONE_VSPACING+200, new int[]{120,246,254}).withCenterOffset());
            attachPlayerAbility(getOwner(), attachedConst, record);
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(PlayerRuleCheckData data)
        {
            return data.getSourceAbility() instanceof ActionAbility &&
                   data.getSourceCardIndex() != null && CardType.isLRIG(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onActionEff2()
        {
            draw(2);
        }
    }
}
