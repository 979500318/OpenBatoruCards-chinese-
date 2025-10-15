package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_PantomimeGreatTrap extends Card {

    public SIGNI_W3_PantomimeGreatTrap()
    {
        setImageSets("WXDi-P11-040", "SPDi02-22");

        setOriginalName("大罠　パントマイム");
        setAltNames("ダイビンパントマイム Daipin Pantomaimu");
        setDescription("jp",
                "@C：対戦相手のターンの間、対戦相手は、能力か効果で対象を選ぶ際、可能性ならばこのシグニを対象とする。\n" +
                "@U $T1：対戦相手のターンの間、このシグニが対戦相手の、能力か効果の対象になったとき、あなたの他のシグニ１体を対象とし、ターン終了時まで、それは[[シャドウ]]を得る。" +
                "~#：カードを１枚引く。その後、あなたのライフクロス１枚を手札に加えてもよい。そうした場合、あなたの手札を１枚ライフクロスに加える。"
        );

        setName("en", "Pantomime, Master Trickster");
        setDescription("en",
                "@C: During your opponent's turn, as your opponent chooses targets for an ability or effect, they must target this SIGNI if possible.\n" +
                "@U $T1: During your opponent's turn, when this SIGNI on your field becomes the target of your opponent's ability or effect, another target SIGNI on your field gains [[Shadow]] until end of turn." +
                "~#Draw a card. Then, you may add one of your Life Cloth to your hand. If you do, add a card from your hand to your Life Cloth."
        );
        
        setName("en_fan", "Pantomime, Great Trap");
        setDescription("en_fan",
                "@C: During your opponent's turn, if your opponent would choose a target for an ability or effect, they must target this SIGNI if able.\n" +
                "@U $T1: During your opponent's turn, when this SIGNI is targeted by your opponent's ability or effect, target 1 of your other SIGNI, and until end of turn, it gains [[Shadow]]." +
                "~#Draw 1 card. Then, you may add 1 card from your life cloth to your hand. If you do, add 1 card from your hand to life cloth."
        );

		setName("zh_simplified", "大罠 默剧");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，如果对战对手的，能力或效果选对象时，能把这只精灵作为对象，则必须把这只精灵作为对象。\n" +
                "@U $T1 :对战对手的回合期间，当这只精灵被作为对战对手的，能力或效果的对象时，你的其他的精灵1只作为对象，直到回合结束时为止，其得到[[暗影]]。" +
                "~#抽1张牌。然后，可以把你的生命护甲1张加入手牌。这样做的场合，你的手牌1张加入生命护甲。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            auto.setUseLimit(UseLimit.TURN, 1);
            
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().except(getCardIndex())).get();
            if(target != null) attachAbility(target, new StockAbilityShadow(), ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            draw(1);
            
            if(getLifeClothCount(getOwner()) > 0 && playerChoiceActivate() && addToHand(CardLocation.LIFE_CLOTH))
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().fromHand()).get();
                addToLifeCloth(cardIndex);
            }
        }
    }
}
