package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_MoeKazekura extends Card {

    public SIGNI_B1_MoeKazekura()
    {
        setImageSets("WX25-CP1-065");

        setOriginalName("風倉モエ");
        setAltNames("カゼクラモエ Kazekura Moe");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－2000する。このターン、対戦相手のライフクロス１枚がクラッシュされたとき、ターン終了時まで、それのパワーを－2000する。" +
                "~{{U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Kazekura Moe");

        setName("en_fan", "Moe Kazekura");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 1 <<Blue Archive>> card from your hand. If you do, until end of turn, it gets --2000 power. This turn, whenever 1 of your opponent's life cloth is crushed, until end of turn, it gets --2000 power." +
                "~{{U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "风仓萌绘");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把<<ブルアカ>>牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-2000。这个回合，当对战对手的生命护甲1张被击溃时，直到回合结束时为止，其的力量-2000。\n" +
                "~{{U:你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                if(discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
                {
                    gainPower(target, -2000, ChronoDuration.turnEnd());
                }
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, caller2 -> gainPower(target, -2000, ChronoDuration.turnEnd()));
                attachedAuto.setCondition(caller2 -> !isOwnCard(caller2) ? ConditionState.OK : ConditionState.BAD);
                
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                attachPlayerAbility(getOwner(), attachedAuto, record);
            }
        }
        
        private void onAutoEff2(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
