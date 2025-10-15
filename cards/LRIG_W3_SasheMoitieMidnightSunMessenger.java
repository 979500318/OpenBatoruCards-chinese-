package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIG_W3_SasheMoitieMidnightSunMessenger extends Card {

    public LRIG_W3_SasheMoitieMidnightSunMessenger()
    {
        setImageSets("WXDi-P11-005", "WXDi-P11-005U");

        setOriginalName("白夜の使者　サシェ・モティエ");
        setAltNames("ビャクヤノシシャサシェモティエ Byakuya no Shisha Sashe Motie");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたの白のシグニ１体が場に出たとき、対戦相手のシグニ１体を対象とし、%W %W %Xを支払ってもよい。そうした場合、それを手札に戻す。\n" +
                "@A $T1 %W0：あなたの白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋2000する。\n" +
                "@A $G1 %W0：次の対戦相手のターンの間、対戦相手は@>@C：あなたは%X %Xを支払わないかぎりシグニでアタックできない。@@を得る。"
        );

        setName("en", "Sashe Motier, Midnight Sun Emissary");
        setDescription("en",
                "@U $T1: During your turn, when a white SIGNI enters your field, you may pay %W %W %X. If you do, return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@A $T1 %W0: Target white SIGNI on your field gets +2000 power until the end of your opponent's next end phase.\n" +
                "@A $G1 %W0: During your opponent's next turn, your opponent gains@>@C: You cannot attack with SIGNI unless you pay %X %X."
        );
        
        setName("en_fan", "Sashe Moitié, Midnight Sun Messenger");
        setDescription("en_fan",
                "@U $T1: During your turn, when 1 of your white SIGNI enters the field, target 1 of your opponent's SIGNI, and you may pay %W %W %X. If you do, return it to their hand.\n" +
                "@A $T1 %W0: Target 1 of your white SIGNI, and until the end of your opponent's next turn, it gets +2000 power.\n" +
                "@A $G1 %W0: During your opponent's next turn, your opponent gains:" +
                "@>@C: Your SIGNI can't attack unless you pay %X %X."
        );

		setName("zh_simplified", "白夜的使者 莎榭·半月");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当你的白色的精灵1只出场时，对战对手的精灵1只作为对象，可以支付%W %W%X。这样做的场合，将其返回手牌。\n" +
                "@A $T1 %W0:你的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000。\n" +
                "@A $G1 %W0:下一个对战对手的回合期间，对战对手得到\n" +
                "@>@C 你如果不把%X %X:支付，那么精灵不能攻击。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) &&
                    CardType.isSIGNI(caller.getCardReference().getType()) && caller.getIndexedInstance().getColor().matches(CardColor.WHITE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();

            if(target != null && payEner(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1)))
            {
                addToHand(target);
            }
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.WHITE)).get();
            gainPower(target, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onActionEff2()
        {
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(2)))
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachPlayerAbility(getOpponent(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
