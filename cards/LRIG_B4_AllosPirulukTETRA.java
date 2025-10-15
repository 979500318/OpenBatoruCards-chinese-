package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventDamage;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXZoneWall;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIG_B4_AllosPirulukTETRA extends Card {

    public LRIG_B4_AllosPirulukTETRA()
    {
        setImageSets("WXK01-002");

        setOriginalName("アロス・ピルルク　TETRA");
        setAltNames("アロスピルルクテトラ Arosu Piruruku Tetora");
        setDescription("jp",
                "@C：あなたの手札が０枚であるかぎり、あなたが対戦相手のルリグによってダメージを受ける場合、代わりにダメージを受けず、ターン終了時まで、この能力を失う。\n" +
                "@A $G1 @[@|カタルシス|@]@ #C：次のあなたのメインフェイズまで、このルリグの基本リミットは１２になりあなたは対戦相手のルリグによってダメージを受けない。あなたが次のあなたのドローフェイズにカードを１枚引く場合、代わりにカードを２枚引く。"
        );

        setName("en", "Allos Piruluk TETRA");
        setDescription("en",
                "@C: As long as there are 0 cards in your hand, if you would be damaged by your opponent's LRIG, instead you aren't damaged, and until end of turn, this LRIG loses this ability.\n" +
                "@A $G1 @[@|Catharsis|@]@ #C: Until your next main phase, this LRIG's base limit becomes 12 and you can't be damaged by your opponent's LRIG. During your next draw phase, if you would draw a card, instead draw 2 cards."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 TETRA");
        setDescription("zh_simplified", 
                "@C :你的手牌在0张时，你因为对战对手的分身受到伤害的场合，作为替代，不会受到伤害，直到回合结束时为止，这个能力失去。\n" +
                "@A $G1 净罪#C:直到下一个你的主要阶段为止，这只分身的基本界限变为12，你不会因为对战对手的分身受到伤害。你在下一个你的抽牌阶段抽1张牌的场合，作为替代，抽2张牌。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(4);
        setLimit(11);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler))
            );
            cont.setOnAbilityInit(() -> GFXZoneWall.attachToAbility(cont, new GFXZoneWall(CardIndex.getOriginalOwner(cardId), CardLocation.LIFE_CLOTH, "LRIG", new int[]{50,150,205})));

            ActionAbility act = registerActionAbility(new CoinCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Catharsis");
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventDamage)event).getPlayer() == sourceAbilityRC.getAbilityOwner() &&
                   !isOwnCard(event.getSource()) && CardType.isLRIG(event.getSource().getCardReference().getType());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            sourceAbilityRC.disable(ChronoDuration.turnEnd());
        }
        
        private void onActionEff()
        {
            setBaseValue(getLRIG(getOwner()), getLRIG(getOwner()).getIndexedInstance().getLimit(),12d, ChronoDuration.nextPhase(getOwner(), GamePhase.MAIN));
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextPhase(getOwner(), GamePhase.MAIN));
            GFXZoneWall.attachToChronoRecord(record, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "generic", new int[]{50,150,205}));
            GFXZoneUnderIndicator.attachToChronoRecord(record, new GFXZoneUnderIndicator(getOwner(), CardLocation.LRIG, "limit_up", 0, new int[]{20,80,255}));
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_DAMAGED, getOwner(), record, data ->
                !isOwnCard(data.getSourceCardIndex()) && CardType.isLRIG(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );

            setPlayerRuleValue(getOwner(), PlayerRuleValueType.DRAW_PHASE_MULTIPLIER, 2, ChronoDuration.nextPhase(getOwner(), GamePhase.MAIN));
        }
    }
}
