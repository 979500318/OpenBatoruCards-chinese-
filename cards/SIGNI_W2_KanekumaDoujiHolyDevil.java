package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_KanekumaDoujiHolyDevil extends Card {

    public SIGNI_W2_KanekumaDoujiHolyDevil()
    {
        setImageSets("WX24-P4-059");

        setOriginalName("聖魔　カネクマドウジ");
        setAltNames("セイマカネクマドウジ Seima Kanekuma Douji");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、このシグニを場からトラッシュに置き%Xを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Kanekuma-Douji, Holy Devil");
        setDescription("en",
                "@U: At the beginning of your main phase, target 1 #G @[Guard]@ SIGNI from your trash, and you may put this SIGNI from the field into the trash and pay %X. If you do, add it to your hand.\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C@#: Can't attack."
        );

		setName("zh_simplified", "圣魔 金熊童子");
        setDescription("zh_simplified", 
                "@U 你的主要阶段开始时，从你的废弃区把持有#G的精灵1张作为对象，可以把这只精灵从场上放置到废弃区并支付%X。这样做的场合，将其加入手牌。\n" +
                "@A #D:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

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
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && payAll(new TrashCost(), new EnerCost(Cost.colorless(1))))
            {
                addToHand(target);
            }
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
