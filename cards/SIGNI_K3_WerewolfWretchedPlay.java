package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K3_WerewolfWretchedPlay extends Card {

    public SIGNI_K3_WerewolfWretchedPlay()
    {
        setImageSets("WXDi-P09-045");

        setOriginalName("惨之遊姫　ジンロウ");
        setAltNames("サンノユウキジンロウ San no Yuuki Jinrou");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたのシグニが１体以上トラッシュから場に出ていた場合、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n\n" +
                "@A @[手札を３枚捨てる]@：あなたのトラッシュからこのカードを場に出す。"
        );

        setName("en", "Werewolf, Wretched Play Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if one or more SIGNI entered your field from the trash this turn, you may pay %K. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n\n" +
                "@A @[Discard three cards]@: Put this card from your trash onto your field. "
        );
        
        setName("en_fan", "Werewolf, Wretched Play");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if 1 or more SIGNI entered your field from the trash this turn, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --10000 power.\n\n" +
                "@A @[Discard 3 cards from your hand]@: Put this card from your trash onto the field."
        );

		setName("zh_simplified", "惨之游姬 人狼杀");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你的精灵有1只以上从废弃区出场的场合，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@A 手牌3张舍弃:从你的废弃区把这张牌出场。（这个能力只有在这张牌在废弃区的场合才能使用）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new DiscardCost(3), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setActiveLocation(CardLocation.TRASH);
        }

        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(e -> e.getId() == GameEventId.ENTER && isOwnCard(e.getCaller()) &&
                CardType.isSIGNI(e.getCaller().getCardReference().getType()) && e.getCaller().getOldLocation() == CardLocation.TRASH) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    gainPower(target, -10000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private ConditionState onActionEffCond()
        {
            return isPlayable() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            putOnField(getCardIndex());
        }
    }
}
