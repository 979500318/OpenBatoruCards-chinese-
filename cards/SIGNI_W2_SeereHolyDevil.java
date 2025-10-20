package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_W2_SeereHolyDevil extends Card {

    public SIGNI_W2_SeereHolyDevil()
    {
        setImageSets("WX24-P2-063");

        setOriginalName("聖魔　セーレ");
        setAltNames("セイマセーレ Seima Seere");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、対戦相手のパワー10000以下のシグニ１体を対象とし、このシグニを場からトラッシュに置き%Xを支払ってもよい。そうした場合、それを手札に戻す。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。"
        );

        setName("en", "Seere, Holy Devil");
        setDescription("en",
                "@U: At the beginning of your main phase, target 1 of your opponent's SIGNI with power 10000 or less, and you may put this SIGNI from the field into the trash and pay %X. If you do, return it to their hand.\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "圣魔 系尔");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，对战对手的力量10000以下的精灵1只作为对象，可以把这只精灵从场上放置到废弃区并支付%X。这样做的场合，将其返回手牌。\n" +
                "@A 横置:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerActionAbility(new DownCost(), this::onActionEff1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            if(target != null && payAll(new TrashCost(), new EnerCost(Cost.colorless(1))))
            {
                addToHand(target);
            }
        }

        private void onActionEff1()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
