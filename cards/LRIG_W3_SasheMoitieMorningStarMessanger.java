package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIG_W3_SasheMoitieMorningStarMessanger extends Card {

    public LRIG_W3_SasheMoitieMorningStarMessanger()
    {
        setImageSets("WX25-P2-014", "WX25-P2-014U");

        setOriginalName("明星の使者　サシェ・モティエ");
        setAltNames("ミョウジョウノシシャサシェモティエ Myoujou no Shisha Sashe Motie");
        setDescription("jp",
                "@C $TP：あなたの場に＜宇宙＞のシグニがあるかぎり、対戦相手は%X %Xを支払わないかぎりルリグでアタックできない。\n" +
                "@A $G1 @[@|レリース|@]@ %W0：次のあなたのエナフェイズ終了時まで、このルリグのリミットを＋１する。次の対戦相手のメインフェイズの間、対戦相手のセンタールリグのリミットを－２する。"
        );

        setName("en", "Sashe Moitié, Morning Star Messanger");
        setDescription("en",
                "@C $TP: As long as there is a <<Space>> SIGNI on your field, your opponent can't attack with their LRIGs unless they pay %X %X.\n" +
                "@A $G1 @[@|Release|@]@ %W0: Until the end of your next ener phase, this LRIG gets +1 limit. During your opponent's next main phase, your opponent's center LRIG gets --2 limit."
        );

		setName("zh_simplified", "明星的使者 莎榭·半月");
        setDescription("zh_simplified", 
                "@C $TP 你的场上有<<宇宙>>精灵时，对战对手如果不把%X %X:支付，那么分身不能攻击。\n" +
                "@A $G1 释放%W0:直到下一个你的充能阶段结束时为止，这只分身的界限+1。下一个对战对手的主要阶段期间，对战对手的核心分身的界限-2。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().OP().anyLRIG(),
                new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(2)))
            );

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Release");
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.SPACE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onActionEff()
        {
            gainValue(getCardIndex(), getLimit(),1d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().LRIG(),
                new ModifiableValueModifier<>(this::onAttachedConstEffModGetSample, () -> -2d)
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            GFXZoneUnderIndicator.attachToAbility(attachedConst, new GFXZoneUnderIndicator(getOpponent(), CardLocation.LRIG, "limit_down"));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.MAIN));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLimit();
        }
    }
}
