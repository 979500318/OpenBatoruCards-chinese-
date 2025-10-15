package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_ReisaUzawa extends Card {

    public SIGNI_W3_ReisaUzawa()
    {
        setImageSets("WX25-CP1-060");

        setOriginalName("宇沢レイサ");
        setAltNames("ウザワレイサ Uzawa Reisa");
        setDescription("jp",
                "@C $TP：対戦相手は、能力か効果で対象を選ぶ際、可能ならばこのシグニを対象とする。\n" +
                "@U $TP：このシグニが対戦相手の、能力か効果の対象になったとき、あなたの場に他の＜ブルアカ＞のシグニがある場合、このシグニを裏向きにし、表向きにする。ターン終了時まで、この方法で表向きになったシグニは@U能力を失う。" +
                "~{{C $TP：このシグニのパワーは＋5000される。@@" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Usawa Reisa");

        setName("en_fan", "Reisa Usawa");
        setDescription("en",
                "@C $TP: If your opponent would choose a target for an ability or effect, they must target this SIGNI if able.\n" +
                "@U $TP: When this SIGNI is targeted by your opponent's ability or effect, if there is another <<Blue Archive>> SIGNI on your field, turn this SIGNI face down, then face up. Until end of turn, the SIGNI that was turned face up this way loses its @U abilities." +
                "~{{C $TP: This SIGNI gets +5000 power.@@" +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );

		setName("zh_simplified", "宇泽玲纱");
        setDescription("zh_simplified", 
                "@C $TP :如果对战对手的，能力或效果选对象时，能把这只精灵作为对象，则必须把这只精灵作为对象。\n" +
                "@U $TP 当这只精灵被作为对战对手的，能力或效果的对象时，你的场上有其他的<<ブルアカ>>精灵的场合，这只精灵变为里向，再变为表向。直到回合结束时为止，这个方法变为表向的精灵的@U能力失去。\n" +
                "~{{C$TP :这只精灵的力量+5000。@@" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.MUST_BE_TARGETED, this::onConstEffModRuleCheck));
            
            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ConstantAbility cont = registerConstantAbility(new PowerModifier(5000));
            cont.setCondition(this::onConstEffCond);
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getGenericData(0) != getOwner() && data.getSourceAbility() != null ? RuleCheckState.OK : RuleCheckState.IGNORE;
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnTurn() && getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    EventTarget.getDataSourceTargetRole() != caller.getIndexedInstance().getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                if(flip(getCardIndex(), CardFace.BACK) &&
                   flip(getCardIndex(), CardFace.FRONT))
                {
                    disableAllAbilities(getCardIndex(), ability -> ability instanceof AutoAbility, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
        }
    }
}
