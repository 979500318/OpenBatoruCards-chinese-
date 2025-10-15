package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_W3_MineAomori extends Card {

    public SIGNI_W3_MineAomori()
    {
        setImageSets("WXDi-CP02-053");

        setOriginalName("蒼森ミネ");
        setAltNames("アオモリミネ Aomori Mine");
        setDescription("jp",
                "@U $T1：対戦相手のターンの間、シグニ１体がアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。\n" +
                "@U：あなたのターン終了時、あなたの他の＜ブルアカ＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋10000する。" +
                "~{{A @[アップ状態のルリグ２体をダウンする]@：対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。@@" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Aomori Mine");
        setDescription("en",
                "\n@U $T1: During your opponent's turn, when a SIGNI attacks, target SIGNI on your opponent's field loses its abilities until end of turn.\n@U: At the end of your turn, another target <<Blue Archive>> SIGNI on your field gets +10000 power until the end of your opponent's next end phase.~{{A @[Down two upped LRIG]@: Return target level one SIGNI on your opponent's field to its owner's hand.@@" +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Mine Aomori");
        setDescription("en_fan",
                "@U $T1: During your opponent's turn, when 1 SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.\n" +
                "@U: At the end of your turn, target 1 of your other <<Blue Archive>> SIGNI, and until the end of your opponent's next turn, it gets +10000 power." +
                "~{{A @[Down 2 of your upped LRIG]@: Target 1 of your opponent's level 1 SIGNI, and return it to their hand.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "苍森美弥");
        setDescription("zh_simplified", 
                "@U $T1 :对战对手的回合期间，当精灵1只攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "@U :你的回合结束时，你的其他的<<ブルアカ>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+10000。\n" +
                "~{{A竖直状态的分身2只#D:对战对手的等级1的精灵1只作为对象，将其返回手牌。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            ActionAbility act = registerActionAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onActionEff);
            act.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnTurn() && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            gainPower(target, 10000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
