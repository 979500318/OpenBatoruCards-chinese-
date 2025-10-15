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
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K1_HaityTHEDOORWickedDevil extends Card {

    public SIGNI_K1_HaityTHEDOORWickedDevil()
    {
        setImageSets("WXDi-P16-081");

        setOriginalName("凶魔　ハイティ//THE DOOR");
        setAltNames("キョウマハイティザドアー Kyouma Haiti Za Doaa");
        setDescription("jp",
                "@A @[このシグニを場からトラッシュに置く]@：このターンにあなたが#Cを合計２枚以上支払っていた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。\n" +
                "@A $T1 #C #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Haity//THE DOOR, Doomed Evil");
        setDescription("en",
                "@A @[Put this SIGNI on your field into its owner's trash]@: If you have paid a total of two or more #C this turn, target SIGNI on your opponent's field gets --3000 power until end of turn.\n@A $T1 #C #C: Target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Haity//THE DOOR, Wicked Devil");
        setDescription("en_fan",
                "@A @[Put this SIGNI from the field into the trash]@: If you paid a total of 2 or more #C this turn, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power.\n" +
                "@A $T1 #C #C: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "凶魔 海蒂//THE DOOR");
        setDescription("zh_simplified", 
                "@A 这只精灵从场上放置到废弃区这个回合你把币:合计2个以上支付过的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "@A $T1 #C #C:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.DEVIL);
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

            registerActionAbility(new TrashCost(), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new CoinCost(2), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
        }

        private void onActionEff1()
        {
            if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -3000, ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}
