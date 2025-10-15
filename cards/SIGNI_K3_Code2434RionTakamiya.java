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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_Code2434RionTakamiya extends Card {

    public SIGNI_K3_Code2434RionTakamiya()
    {
        setImageSets("WXDi-CP01-030");

        setOriginalName("コード２４３４　鷹宮リオン");
        setAltNames("コードニジサンジタカミヤリオン Koodo Nijisanji Takamiya Rion");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にある他の＜バーチャル＞のシグニ１体につき－3000する。\n" +
                "@E %K：あなたのトラッシュからレベル１の＜バーチャル＞のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Takamiya Rion, Code 2434");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --3000 power for each other <<Virtual>> SIGNI on your field until end of turn.\n@E %K: Put target level one <<Virtual>> SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Code 2434 Rion Takamiya");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power for each other <<Virtual>> SIGNI on your field.\n" +
                "@E %K: Target 1 level 1 <<Virtual>> SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "2434代号 鹰宫莉音");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的其他的<<バーチャル>>精灵的数量，每有1只就-3000。\n" +
                "@E %K:从你的废弃区把等级1的<<バーチャル>>精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -3000 * new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount(), ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(1).withClass(CardSIGNIClass.VIRTUAL).fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
