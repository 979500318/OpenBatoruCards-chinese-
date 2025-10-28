package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_R_PassionateBurstOfCourage extends Card {

    public ARTS_R_PassionateBurstOfCourage()
    {
        setImageSets("WX24-P3-003", "WX24-P3-003U");

        setOriginalName("勇気爆熱");
        setAltNames("ユウキバクネツ Yuuki Bakunetsu");
        setDescription("jp",
                "あなたのレベル２以上のセンタールリグ１体を対象とし、次のあなたのエナフェイズ終了時まで、それのリミットを＋１し、それは以下の能力を得る。" +
                "@>@C：あなたの手札が３枚以下であるかぎり、あなたは対戦相手のルリグによってダメージを受けない。\n" +
                "@A $T1 #D：カードを２枚引くか【エナチャージ２】をする。"
        );

        setName("en", "Passionate Burst of Courage");
        setDescription("en",
                "Target your level 2 or higher center LRIG, and until the end of your next ener phase, it gets +1 limit, and it gains:" +
                "@>@C: As long as there 3 or less cards in your hand, you can't be damaged by your opponent's LRIG.\n" +
                "@A $T1 #D: Draw 2 cards or [[Ener Charge 2]]."
        );

        setName("zh_simplified", "勇气爆热");
        setDescription("zh_simplified", 
                "你的等级2以上的核心分身1只作为对象，直到下一个你的充能阶段结束时为止，其的界限+1，其得到以下的能力。" +
                "@>@C :你的手牌在3张以下时，你不会因为对战对手的分身受到伤害。\n" +
                "@A $T1 #D:抽2张牌或[[能量填充2]]。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
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

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }

        private ConditionState onARTSEffCond()
        {
            return new TargetFilter().own().LRIG().withLevel(2,0).getValidTargetsCount() == 0 ? ConditionState.WARN : ConditionState.OK;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(2,0)).get();
            if(target == null) return;
            
            gainValue(target, target.getIndexedInstance().getLimit(),1d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_BE_DAMAGED, TargetFilter.HINT_OWNER_OWN, this::onAttachedConstEffModRuleCheck));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            GFX.attachToAbility(attachedConst, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "generic", new int[]{200,180,80}));
            attachAbility(target, attachedConst, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ActionAbility attachedAct = new ActionAbility(new DownCost(), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachAbility(target, attachedAct, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getHandCount(getOwner()) <= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return !isOwnCard(data.getSourceCardIndex()) && CardType.isLRIG(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        private void onAttachedActionEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(2);
            } else {
                enerCharge(2);
            }
        }
    }
}
