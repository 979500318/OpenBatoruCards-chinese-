package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K1_MahomahoTHEDOORNaturalSource extends Card {

    public SIGNI_K1_MahomahoTHEDOORNaturalSource()
    {
        setImageSets("WXDi-P15-073");

        setOriginalName("羅原　まほまほ//THE DOOR");
        setAltNames("ラゲンマホマホザドアー Ragen Mahomaho Za Doaa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたが#Cを合計１枚以上支払っていた場合、対戦相手のデッキの上からカードを３枚トラッシュに置く。\n" +
                "@A $T2 #C：あなたのデッキの上からカードを５枚見る。その中からカード１枚をトラッシュに置き、残りを好きな順番でデッキの一番下に置く。" +
                "~#：あなたのトラッシュから#Gを持たないレベル２以下のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Mahomaho//THE DOOR, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you have paid a total of one or more #C this turn, put the top three cards of your opponent's deck into their trash.\n@A $T2 #C: Look at the top five cards of your deck. Put a card from among them into your trash and put the rest on the bottom of your deck in any order." +
                "~#Add target level two or less SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Mahomaho//THE DOOR, Natural Source");
        setDescription("en_fan",
                "@U: When this SIGNI attacks, if you paid 1 or more #C this turn, put the top 3 cards of your opponent's deck into the trash.\n" +
                "@A $T2 #C: Look at the top 5 cards of your deck. Put 1 card from among them into the trash, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 level 2 or lower SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "罗原 真帆帆//THE DOOR");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，这个回合你把币:合计1个以上支付过的场合，从对战对手的牌组上面把3张牌放置到废弃区。\n" +
                "@A $T2 #C:从你的牌组上面看5张牌。从中把1张牌放置到废弃区，剩下的任意顺序放置到牌组最下面。" +
                "~#从你的废弃区把不持有#G的等级2以下的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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

            ActionAbility act = registerActionAbility(new CoinCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0) > 0)
            {
                millDeck(getOpponent(), 3);
            }
        }

        private void onActionEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            trash(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withLevel(0,2).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
