package open.batoru.data.cards;

import open.batoru.core.Game;
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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K2_MikomikoTHEDOORNaturalBacteria extends Card {

    public SIGNI_K2_MikomikoTHEDOORNaturalBacteria()
    {
        setImageSets("WXDi-P15-074");

        setOriginalName("羅菌　みこみこ//THE DOOR");
        setAltNames("ラキンミコミコザドアー Rakin Mikomiko Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが#Cを合計１枚以上支払っていた場合、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。このターンにあなたが#Cを合計５枚以上支払っていた場合、代わりにターン終了時まで、それのパワーを－8000する。\n" +
                "@A $T1 #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Mikomiko//THE DOOR, Natural Bacteria");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have paid a total of one or more #C this turn, you may pay %K. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn. If you have paid a total of five or more #C this turn, it gets --8000 power until end of turn instead.\n@A $T1 #C: Target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Mikomiko//THE DOOR, Natural Bacteria");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you paid 1 or more #C this turn, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --5000 power. If you paid a total of 5 or more #C this turn, until end of turn, it gets --8000 power instead.\n" +
                "@A $T1 #C: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "罗菌 美琴琴//THE DOOR");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，这个回合你把币合计1个以上支付过的场合，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-5000。这个回合你把币:合计5个以上支付过的场合，作为替代，直到回合结束时为止，其的力量-8000。\n" +
                "@A $T1 #C:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.BACTERIA);
        setLevel(2);
        setPower(5000);

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

            ActionAbility act = registerActionAbility(new CoinCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int count = Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum();
            
            if(count < 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    gainPower(target, count > -5 ? -5000 : -8000, ChronoDuration.turnEnd());
                }
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
