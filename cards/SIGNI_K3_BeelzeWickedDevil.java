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
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_BeelzeWickedDevil extends Card {

    public SIGNI_K3_BeelzeWickedDevil()
    {
        setImageSets("WX24-D5-19");

        setOriginalName("凶魔　ベルゼ");
        setAltNames("キョウマベルゼ Kyouma Beeruze");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのトラッシュに黒のカードが１５枚以上ある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Beelze, Wicked Devil");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are 15 or more black cards in your trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "凶魔 别西卜");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的废弃区的黑色的牌在15张以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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
            if(new TargetFilter().own().withColor(CardColor.BLACK).fromTrash().getValidTargetsCount() >= 15)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
