package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.LifeBurstAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_R3_HalberdGreatEquipment extends Card {

    public SIGNI_R3_HalberdGreatEquipment()
    {
        setImageSets("WXDi-P08-041");

        setOriginalName("大装　ハルバード");
        setAltNames("タイソウハルバード Taisou Harubaado");
        setDescription("jp",
                "@U $1：あなたのアップ状態のシグニ１体が対戦相手の、アシストルリグかライフバーストの能力か効果の対象になったとき、%R %R %R %Xを支払ってもよい。そうした場合、対戦相手のライフクロス１枚をクラッシュする。\n" +
                "@E %R @[手札を１枚捨てる]@：カードを２枚引く。"
        );

        setName("en", "Halberd, Full Armed");
        setDescription("en",
                "@U $T1: When an upped SIGNI on your field becomes the target of an abilities or effects of your opponent's Assist LRIG or Life Burst, you may pay %R %R %R %X. If you do, crush one of your opponent's Life Cloth.\n" +
                "@E %R @[Discard a card]@: Draw two cards."
        );
        
        setName("en_fan", "Halberd, Great Equipment");
        setDescription("en_fan",
                "@U $T1: When 1 of your upped SIGNI is targeted by the ability or effect of your opponent's assist LRIG or life burst, you may pay %R %R %R %X. If you do, crush 1 of your opponent's life cloth.\n" +
                "@E %R @[Discard 1 card from your hand]@: Draw 2 cards."
        );

		setName("zh_simplified", "大装 斧枪");
        setDescription("zh_simplified", 
                "@U $T1 :当你的竖直状态的精灵1只被作为对战对手的，支援分身或生命迸发的能力或效果的对象时，可以支付%R %R %R%X。这样做的场合，对战对手的生命护甲1张击溃。\n" +
                "@E %R手牌1张舍弃:抽2张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.RED, 1)), new DiscardCost(1)), this::onEnterEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    (getEvent().getSource().getCardReference().getType() == CardType.LRIG_ASSIST || getEvent().getSourceAbility() instanceof LifeBurstAbility) &&
                    isOwnCard(caller) && CardLocation.isSIGNI(caller.getLocation()) && !caller.getIndexedInstance().isState(CardStateFlag.DOWNED) &&
                    EventTarget.getDataSourceTargetRole() != caller.getIndexedInstance().getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(payEner(Cost.color(CardColor.RED, 3) + Cost.colorless(1)))
            {
                crush(getOpponent());
            }
        }

        private void onEnterEff()
        {
            draw(2);
        }
    }
}
