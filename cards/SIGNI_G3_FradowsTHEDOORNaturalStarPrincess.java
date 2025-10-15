package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCoin;
import open.batoru.data.ability.stock.*;

public final class SIGNI_G3_FradowsTHEDOORNaturalStarPrincess extends Card {

    public SIGNI_G3_FradowsTHEDOORNaturalStarPrincess()
    {
        setImageSets("WXDi-P15-052");
        setLinkedImageSets("WXDi-P15-008");

        setOriginalName("羅星姫　フラドウズ//THE DOOR");
        setAltNames("ラセイキフラドウズザドアー Raseiki Furadouzu Za Doaa");
        setDescription("jp",
                "@A %G：あなたの場に《闘争者カーニバル　#T#》がいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1ターン終了時まで、このシグニは【ランサー】を得る。\n" +
                "$$2このターンにあなたが#Cを合計３枚以上支払っていた場合、ターン終了時まで、このシグニは【Ｓランサー】を得る。\n" +
                "@A #C：次の対戦相手のターン終了時まで、このシグニのパワーを＋3000する。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Flatwoods//THE DOOR, Galactic Queen");
        setDescription("en",
                "@A %G: If \"Warrior Carnival #T#\" is on your field, choose one of the following.\n$$1This SIGNI gains [[Lancer]] until end of turn. \n$$2If you have paid a total of three or more #C this turn, this SIGNI gains [[S Lancer]] until end of turn. \n@A #C: This SIGNI gets +3000 power until the end of your opponent's next end phase." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Fradows//THE DOOR, Natural Star Princess");
        setDescription("en_fan",
                "@A %G: If your LRIG is \"Carnival #T#, The Fighter\", @[@|choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, this SIGNI gains [[Lancer]].\n" +
                "$$2 If you paid a total of 3 or more #C this turn, this SIGNI gains [[S Lancer]].\n" +
                "@A #C: Until the end of your opponent's next turn, this SIGNI gets +3000 power." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "罗星姬 弗拉姆斯蒂德星图//THE DOOR");
        setDescription("zh_simplified", 
                "@A %G你的场上有《闘争者カーニバル　#T#》的场合，从以下的2种选1种。\n" +
                "$$1 直到回合结束时为止，这只精灵得到[[枪兵]]。\n" +
                "$$2 这个回合你把币:合计3个以上支付过的场合，直到回合结束时为止，这只精灵得到[[S枪兵]]。\n" +
                "@A #C:直到下一个对战对手的回合结束时为止，这只精灵的力量+3000。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.SPACE);
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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            registerActionAbility(new CoinCost(1), this::onActionEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onActionEff1Cond()
        {
            return !getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("闘争者カーニバル　#T#") ||
                    getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                     ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                     ability.getSourceStockAbility() instanceof StockAbilitySLancer) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff1()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("闘争者カーニバル　#T#"))
            {
                if(playerChoiceMode() == 1)
                {
                    attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
                } else if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                           filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                           mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -3)
                {
                    attachAbility(getCardIndex(), new StockAbilitySLancer(), ChronoDuration.turnEnd());
                }
            }
        }

        private void onActionEff2()
        {
            gainPower(getCardIndex(), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
