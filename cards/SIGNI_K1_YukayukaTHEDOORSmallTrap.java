package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K1_YukayukaTHEDOORSmallTrap extends Card {

    public SIGNI_K1_YukayukaTHEDOORSmallTrap()
    {
        setImageSets("WXDi-P15-072");

        setOriginalName("小罠　ゆかゆか//THE DOOR");
        setAltNames("ショウビンユカユカザドアー Shoubin Yukayuka Za Doaa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが#Cを合計３枚以上支払っていた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "@A $T1 #C #C #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Yukayuka//THE DOOR, Small Trickster");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you have paid a total of three or more #C this turn, target SIGNI on your opponent's field gets --2000 power until end of turn.\n@A $T1 #C #C #C: Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Yukayuka//THE DOOR, Small Trap");
        setDescription("en_fan",
                "@U: When this SIGNI attacks, if you paid a total of 3 or more #C this turn, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n" +
                "@A $T1 #C #C #C: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "小罠 由香香//THE DOOR");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，这个回合你把币:合计3个以上支付过的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@A $T1 #C #C #C:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new CoinCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff()
        {
            if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
