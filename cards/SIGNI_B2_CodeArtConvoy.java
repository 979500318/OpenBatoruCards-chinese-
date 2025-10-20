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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B2_CodeArtConvoy extends Card {

    public SIGNI_B2_CodeArtConvoy()
    {
        setImageSets("PR-Di038");

        setOriginalName("コードアート　コンボイ");
        setAltNames("コードアートコンボイ Koodo Aato Konboi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋2000する。\n" +
                "@A @[手札を１枚捨てる]@：このシグニは覚醒する。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Code Art Convoy");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, until the end of your opponent's next turn, all of your SIGNI get +2000 power.\n" +
                "@A @[Discard 1 card from your hand]@: This SIGNI awakens." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "必杀代号 擎天柱");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵在觉醒状态的场合，直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+2000。\n" +
                "@A 手牌1张舍弃:这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            ActionAbility act = registerActionAbility(new DiscardCost(1), this::onActionEff);
            act.setCondition(this::onActionEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED))
            {
                gainPower(getSIGNIOnField(getOwner()), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private ConditionState onActionEffCond()
        {
            return !isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
