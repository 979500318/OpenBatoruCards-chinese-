package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W3_CodeAncientsSphinx extends Card {

    public SIGNI_W3_CodeAncientsSphinx()
    {
        setImageSets("WX24-P1-041");

        setOriginalName("コードアンシエンツ　スフィンクス");
        setAltNames("コードアンシエンツスフィンクス Koodo Anshientsu Suphinkusu");
        setDescription("jp",
                "@C $TP：このシグニのパワーは＋3000される。\n" +
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のルリグかシグニ１体を対象とし、ターン終了時まで、それは@>@C：%Xを支払わないかぎりアタックできない。@@を得る。\n" +
                "@A %W %X #D：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Code Ancients Sphinx");
        setDescription("en",
                "@C $TP: This SIGNI gets +3000 power.\n" +
                "@U: At the beginning of your opponent's attack phase, target 1 of your opponent's LRIG or SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack unless you pay %X.@@" +
                "@A %W %X #D: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "古神代号 斯芬克斯");
        setDescription("zh_simplified", 
                "@C $TP :这只精灵的力量+3000。\n" +
                "@U :对战对手的攻击阶段开始时，对战对手的分身或精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C 如果不把%X:支付，那么不能攻击。@@\n" +
                "@A %W%X#D从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), new DownCost()), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(1))));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
