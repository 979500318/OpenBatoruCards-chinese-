package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_W1_ShaxHolyDevil extends Card {

    public SIGNI_W1_ShaxHolyDevil()
    {
        setImageSets("WX24-P2-060");

        setOriginalName("聖魔　シャックス");
        setAltNames("セイマシャックス Seima Shakkusu");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、対戦相手のパワー5000以下のシグニ１体を対象とし、このシグニを場からトラッシュに置いてもよい。そうした場合、それを手札に戻す。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。"
        );

        setName("en", "Shax, Holy Devil");
        setDescription("en",
                "@U: At the beginning of your main phase, target 1 of your opponent's SIGNI with power 5000 or less, and you may put this SIGNI from the field into the trash. If you do, return it to their hand.\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "圣魔 沙克斯");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，对战对手的力量5000以下的精灵1只作为对象，可以把这只精灵从场上放置到废弃区。这样做的场合，将其返回手牌。\n" +
                "@A 横置:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(3000);

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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,5000)).get();
            if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate() && trash(getCardIndex()))
            {
                addToHand(target);
            }
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
