package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K3_CoinTossTHEDOORWretchedPlayPrincess extends Card {

    public SIGNI_K3_CoinTossTHEDOORWretchedPlayPrincess()
    {
        setImageSets("WXDi-P15-054");
        setLinkedImageSets("WXDi-P15-009");

        setOriginalName("惨之遊姫　コイントス//THE DOOR");
        setAltNames("サンノユウキコイントスザドアー San no Yuuki Koin Tosu Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《貪欲の駄姫　闘争者グズ子》がいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。\n" +
                "$$2このターンにあなたが#Cを合計１枚以上支払っていた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "@A $T1 %K #C #C #C：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Coin Toss//THE DOOR, Tragic Party");
        setDescription("en",
                "@U: At the beginning of your attack phase, if \"Warrior Guzuko, Queen of Greed\" is on your field, choose one of the following.\n$$1Target SIGNI on your opponent's field gets --5000 power until end of turn. \n$$2If you have paid a total of one or more #C this turn, target SIGNI on your opponent's field gets --8000 power until end of turn.\n@A $T1 %K #C #C #C: Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Coin Toss//THE DOOR, Wretched Play Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if your LRIG is \"Fighter Guzuko, Useless Princess of Greed\", @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.\n" +
                "$$2 If you paid 1 or more #C this turn, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@A $T1 %K #C #C #C: Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "惨之游姬 掷硬币//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《貪欲の駄姫　闘争者グズ子》的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "$$2 这个回合你把币:合计1个以上支付过的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@A $T1 %K#C #C #C:从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLACK, 1)), new CoinCost(3)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("貪欲の駄姫　闘争者グズ子"))
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    gainPower(target, -5000, ChronoDuration.turnEnd());
                } else if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0) > 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    gainPower(target, -8000, ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
