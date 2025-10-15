package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class LRIGA_W2_DonaSendEmFlying extends Card {

    public LRIGA_W2_DonaSendEmFlying()
    {
        setImageSets("WX25-P2-049");

        setOriginalName("ドーナ『かっ飛ばす！』");
        setAltNames("ドーナカットバス Doona Kattobasu");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U $T1：あなたのシグニ１体が場を離れたとき、対戦相手のシグニ１体を対象とし、それを手札に戻す。@@を得る。"
        );

        setName("en", "Dona \"Send 'em Flying!\"");
        setDescription("en",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When 1 of your SIGNI leaves the field, target 1 of your opponent's SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "多娜『击飞！』");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当你的精灵1只离场时，对战对手的精灵1只作为对象，将其返回手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.MOVE, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.isSIGNIOnField() && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
    }
}
