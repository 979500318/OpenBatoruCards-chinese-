package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_K3_JabberwockGreatTrap extends Card {

    public SIGNI_K3_JabberwockGreatTrap()
    {
        setImageSets("WDK04-014");

        setOriginalName("大罠　ジャバウォック");
        setAltNames("ダイビンジャバウォック Daibin Jabawokku");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニが中央のシグニゾーンにある場合、あなたのデッキの一番上を公開する。それをデッキの一番下に置いてもよい。この方法で公開したカードがレベルが奇数のシグニの場合、ターン終了時まで、このシグニのパワーは＋5000され、このシグニは【ランサー】を得る。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Jabberwock, Great Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI is in your center SIGNI zone, reveal the top card of your deck. You may put it on the bottom of your deck. If it was a SIGNI with an odd level, until end of turn, this SIGNI gets +5000 power, and it gains [[Lancer]]." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "大罠 瞎扯龙");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵在中央的精灵区的场合，你的牌组最上面公开。可以将其放置到牌组最下面。这个方法公开的牌是等级在奇数的精灵的场合，直到回合结束时为止，这只精灵的力量+5000，这只精灵得到[[枪兵]]。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.SIGNI_CENTER)
            {
                CardIndex cardIndex = reveal();

                if(cardIndex != null)
                {
                    boolean match = CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && (cardIndex.getIndexedInstance().getLevelByRef() % 2) != 0;
                    
                    returnToDeck(cardIndex, playerChoiceAction(ActionHint.BOTTOM, ActionHint.TOP) == 1 ? DeckPosition.BOTTOM : DeckPosition.TOP);
                    
                    if(match)
                    {
                        gainPower(getCardIndex(), 5000, ChronoDuration.turnEnd());
                        attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
                    }
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
