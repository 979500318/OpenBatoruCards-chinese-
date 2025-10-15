package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_G1_MilulunTHEDOORNaturalSource extends Card {

    public SIGNI_G1_MilulunTHEDOORNaturalSource()
    {
        setImageSets("WXDi-P15-068");

        setOriginalName("羅原　ミルルン//THE DOOR");
        setAltNames("ラゲンミルルンザドアー Ragen Mirurun Za Doaa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが#Cを合計２枚以上支払っていた場合、【エナチャージ１】をする。\n" +
                "@A $T1 #C #C：【エナチャージ１】"
        );

        setName("en", "Milulun//THE DOOR, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you have paid a total of two or more #C this turn, [[Ener Charge 1]].\n@A $T1 #C #C: [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Milulun//THE DOOR, Natural Source");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if you paid a total of 2 or more #C this turn, [[Ener Charge 1]].\n" +
                "@A $T1 #C #C: [[Ener Charge 1]]"
        );

		setName("zh_simplified", "罗原 米璐璐恩//THE DOOR");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，这个回合你把币:合计2个以上支付过的场合，[[能量填充1]]。\n" +
                "@A $T1 #C #C:[[能量填充1]]\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.ATOM);
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

            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff()
        {
            if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -2)
            {
                enerCharge(1);
            }
        }

        private void onActionEff()
        {
            enerCharge(1);
        }
    }
}
