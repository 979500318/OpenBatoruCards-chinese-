package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_W3_Code2434FurenELustario extends Card {

    public SIGNI_W3_Code2434FurenELustario()
    {
        setImageSets("WXDi-CP01-027");
        setLinkedImageSets("WXDi-P00-048");

        setOriginalName("コード２４３４　フレン・Ｅ・ルスタリオ");
        setAltNames("コードニジサンジフレンイールスタリオ Koodo Nijisanji Furen Ii Rusutario");
        setDescription("jp",
                "@C：あなたの《フレン・スラッシュ》の使用コストは%X %X %X減る。\n" +
                "@U $T1：あなたの場に＜バーチャル＞のシグニが３体ある間、対戦相手のシグニ１体があなたの効果によって手札に戻ったとき、カードを１枚引くか【エナチャージ１】をする。\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー10000以下のシグニ１体を対象とし、手札から#Gを持つシグニを１枚捨ててもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "Furen E Lustario, Code 2434");
        setDescription("en",
                "@C: Use costs of your \"Furen Slash\" are reduced by %X %X %X.\n@U $T1: While there are three <<Virtual>> SIGNI on your field, when a SIGNI on your opponent's field is returned to its owner's hand by your effect, draw a card or [[Ener Charge 1]].\n@U: At the beginning of your attack phase, you may discard a SIGNI with a #G. If you do, return target SIGNI on your opponent's field with power 10000 or less to its owner's hand."
        );
        
        setName("en_fan", "Code 2434 Furen E Lustario");
        setDescription("en_fan",
                "@C: The use cost of your \"Furen Slash\" is reduced by %X %X %X.\n" +
                "@U $T1: While there are 3 <<Virtual>> SIGNI on your field, when 1 of your opponent's SIGNI is returned to their hand by your effect, draw 1 card or [[Ener Charge 1]].\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 10000 or less, and you may discard 1 #G @[Guard]@ SIGNI from your hand. If you do, return it to their hand."
        );

		setName("zh_simplified", "2434代号 芙莲·E·露丝塔莉欧");
        setDescription("zh_simplified", 
                "@C :你的《フレン・スラッシュ》的使用费用减%X %X %X。\n" +
                "@U $T1 :你的场上的<<バーチャル>>精灵在3只期间，当对战对手的精灵1只因为你的效果返回手牌时，抽1张牌或[[能量填充1]]。\n" +
                "@U 你的攻击阶段开始时，对战对手的力量10000以下的精灵1只作为对象，可以从手牌把持有#G的精灵1张舍弃。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            registerConstantAbility(new TargetFilter().own().spell().withName("フレン・スラッシュ").anyLocation(),
                new CostModifier(() -> new EnerCost(Cost.colorless(3)), ModifierMode.REDUCE)
            );

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 3 &&
                   CardLocation.isSIGNI(caller.getLocation()) && EventMove.getDataMoveLocation() == CardLocation.HAND &&
                   !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            
            if(target != null && discard(0,1, new TargetFilter().SIGNI().withState(CardStateFlag.CAN_GUARD)).get() != null)
            {
                addToHand(target);
            }
        }
    }
}
