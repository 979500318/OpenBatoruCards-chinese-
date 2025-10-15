package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckMustRuleCheckBeBlocked;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.game._3d.Group3D;
import open.batoru.game.animations.AnimationSpinnerRotateCustom;
import open.batoru.game.gfx.GFXZoneSpinner;
import open.batoru.game.gfx.GFXZoneSpinner.GFXFlatSpinnerObject;

public final class LRIG_R4_ChiyoriFinalChapter extends Card {

    public LRIG_R4_ChiyoriFinalChapter()
    {
        setImageSets("WX24-P4-016", "WX24-P4-016U");

        setOriginalName("ちより　最終章");
        setAltNames("チヨリサイシュウショウ Chiyori Saishuushou");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜ちより＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：カードを２枚引くか【エナチャージ２】をする。\n" +
                "@A $G1 @[@|ファンタジー|@]@ %R %X：&E４枚以上@0このターン、あなたの効果によってシグニのアタックは無効にならない。このターンのアタックフェイズの間、効果によってあなたの【マジックボックス】１つが表向きになったとき、あなたのシグニ１体を対象とし、ターン終了時まで、それは【アサシン】か【ダブルクラッシュ】を得る。"
        );

        setName("en", "Chiyori, Final Chapter");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Chiyori>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Draw 2 cards or [[Ener Charge 2]].\n" +
                "@A $G1 @[@|Fantasy|@]@ %R %X: &E4 or more@0 This turn, your SIGNI's attacks can't be disabled by your effects. During the attack phase of this turn, when 1 of your [[Magic Box]] is turned face-up by an effect, target 1 of your SIGNI, and until end of turn, it gains [[Assassin]] or [[Double Crush]]."
        );

		setName("zh_simplified", "千依 最终章");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<ちより>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:抽2张牌或[[能量填充2]]。\n" +
                "@A $G1 :幻想%R%X&E4张以上@0这个回合，不会因为你的效果把精灵的攻击无效。这个回合的攻击阶段期间，当因为效果把你的[[魔术箱]]1个表向时，你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]或[[双重击溃]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CHIYORI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(4);
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.CHIYORI).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Fantasy");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(2);
            } else {
                enerCharge(2);
            }
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(),
                    new RuleCheckModifier<>(CardRuleCheckType.MUST_BE_BLOCKED, this::onAttachedConstModRuleCheck)
                );
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.TRASH, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                Group3D[] spinNodes = new Group3D[8];
                for(int i=0;i<spinNodes.length;i++) spinNodes[i] = new GFXFlatSpinnerObject("magic_box/star", new int[]{255,255,0});
                GFXZoneSpinner spinner = new GFXZoneSpinner(getOwner(), CardLocation.LRIG, new AnimationSpinnerRotateCustom(4500, 200, -50), spinNodes);
                GFXZoneSpinner.attachToAbility(attachedAuto, spinner);
                
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private RuleCheckState onAttachedConstModRuleCheck(CardRuleCheckData data)
        {
            return RuleCheckMustRuleCheckBeBlocked.getDataSourceRuleCheck(data) == data.getCardIndex().getIndexedInstance().getRCRegistry().getRuleCheck(CardRuleCheckType.CAN_LAND_ATTACK) &&
                    isOwnCard(data.getSourceAbilityRC().getSourceCardIndex()) ? RuleCheckState.OK : RuleCheckState.IGNORE;
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && isOwnCard(caller) &&
                    caller.getUnderType() == CardUnderType.ZONE_MAGIC_BOX &&
                    getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, playerChoiceAction(ActionHint.ASSASSIN, ActionHint.DOUBLECRUSH) == 1 ? new StockAbilityAssassin() : new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
