package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckCanPowerBeChanged;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K3_DreiHanareFessone extends Card {

    public SIGNI_K3_DreiHanareFessone()
    {
        setImageSets("WXDi-P14-048", "WXDi-P14-048P");

        setOriginalName("ドライ＝ハナレ//フェゾーネ");
        setAltNames("ドライハナレフェゾーネ Dorei Hanare Fezoone");
        setDescription("jp",
                "@C：対戦相手の@C能力の効果によって、シグニのパワーは＋（プラス）されない。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのトラッシュにカードが１０枚以上ある場合、%Kを支払ってもよい。そうした場合、ターン終了時まで、対戦相手のすべてのシグニのパワーを－3000する。"
        );

        setName("en", "Hanare//Fesonne, Type: Drei");
        setDescription("en",
                "@C: The power of a SIGNI on your opponent's field cannot be + by your opponent's @C ability's effects.\n@U: At the beginning of your attack phase, if there are ten or more cards in your trash, you may pay %K. If you do, all SIGNI on your opponent's field get --3000 power until end of turn."
        );
        
        setName("en_fan", "Drei-Hanare//Fessone");
        setDescription("en_fan",
                "@C: The power of your opponent's SIGNI can't be + (plus) by your opponent's @C abilities.\n" +
                "@U: At the beginning of your attack phase, if there are 10 or more cards in your trash, you may pay %K. If you do, until end of turn, all of your opponent's SIGNI get --3000 power."
        );

		setName("zh_simplified", "DREI=离//音乐节");
        setDescription("zh_simplified", 
                "@C 不会因为对战对手的@C能力的效果，把精灵的力量+（加号）。\n" +
                "@U :你的攻击阶段开始时，你的废弃区的牌在10张以上的场合，可以支付%K。这样做的场合，直到回合结束时为止，对战对手的全部的精灵的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().OP().SIGNI(), new RuleCheckModifier<>(CardRuleCheckType.CAN_POWER_BE_CHANGED, this::onConstEffRuleCheck));
            cont.setForcedModifierUpdate(mod -> mod instanceof PowerModifier && (mod.getSourceAbility().getSourceCardId() ^ cardId) < 0);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private RuleCheckState onConstEffRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() instanceof ConstantAbility && !isOwnCard(data.getSourceCardIndex()) &&
                    RuleCheckCanPowerBeChanged.getDataAddValue(data) > 0 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getTrashCount(getOwner()) >= 10 && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                gainPower(getSIGNIOnField(getOpponent()), -3000, ChronoDuration.turnEnd());
            }
        }
    }
}

