package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W3_AriochHolyDevilPrincess extends Card {

    public SIGNI_W3_AriochHolyDevilPrincess()
    {
        setImageSets("WX24-P3-049");

        setOriginalName("聖魔姫　アリオーシュ");
        setAltNames("セイマキアリオーシュ Seimaki Ariooshu");
        setDescription("jp",
                "@U：このシグニが場を離れたとき、対戦相手のルリグ１体を対象とし、%Wを支払ってもよい。そうした場合、ターン終了時まで、それは@>@C：あなたのシグニ１体を場からトラッシュに置かないかぎりアタックできない。@@を得る。\n" +
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、このシグニを場からトラッシュに置いてもよい。そうした場合、ターン終了時まで、それは@>@C：アタックできない。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Arioch, Holy Devil Princess");
        setDescription("en",
                "@U: When this SIGNI leaves the field, target 1 of your opponent's LRIG, and you may pay %W. If you do, until end of turn, it gains:" +
                "@>@C: Can't attack unless you put 1 of your SIGNI from the field into the trash.@@" +
                "@U: At the beginning of your opponent's attack phase, target 1 of your opponent's SIGNI, and you may put this SIGNI from the field into the trash. If you do, until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "圣魔姬 阿里奥克");
        setDescription("zh_simplified", 
                "@U :当这只精灵离场时，对战对手的分身1只作为对象，可以支付%W。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@C :如果不把你的精灵1只从场上放置到废弃区，那么不能攻击。@@\n" +
                "@U :对战对手的攻击阶段开始时，对战对手的精灵1只作为对象，可以把这只精灵从场上放置到废弃区。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond1()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation())  ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK,
                    data -> new TrashCost(new TargetFilter().own().SIGNI())
                ));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEffCond2()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate())
            {
                trash(getCardIndex());
                
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
