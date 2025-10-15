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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_KasumiKinugawa extends Card {

    public SIGNI_K1_KasumiKinugawa()
    {
        setImageSets("WX25-CP1-084");

        setOriginalName("鬼怒川カスミ");
        setAltNames("キヌガワカスミ Kinugawa Kasumi");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：対戦相手のレベル２以下のシグニ１体を対象とし、ターン終了時まで、それは能力を失い、それのパワーを－3000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Kinugawa Kasumi");

        setName("en_fan", "Kasumi Kinugawa");
        setDescription("en",
                "@E @[Discard 1 <<Blue Archive>> card from your hand]@: Target 1 of your opponent's level 2 or lower SIGNI, and until end of turn, it loses its abilities, and it gets --3000 power." +
                "~{{U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "鬼怒川霞");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌1张舍弃:对战对手的等级2以下的精灵1只作为对象，直到回合结束时为止，其的能力失去，其的力量-3000。\n" +
                "~{{U:你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withLevel(0,2)).get();
            
            if(target != null)
            {
                disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
                gainPower(target, -3000, ChronoDuration.turnEnd());
            }
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
