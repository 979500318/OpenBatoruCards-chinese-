package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_HandkerchiefMagicSmallTrap extends Card {

    public SIGNI_R1_HandkerchiefMagicSmallTrap()
    {
        setImageSets("WX24-P4-064");

        setOriginalName("小罠　ハンカチマジック");
        setAltNames("ショウビンハンカチマジック Shoupin Hankachi Majikku");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を見る。そのカードを【マジックボックス】としてあなたのシグニゾーンに設置してもよい。"
        );

        setName("en", "Handkerchief Magic, Small Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, look at the top card of your deck. You may put it onto 1 of your SIGNI zones as a [[Magic Box]]."
        );

		setName("zh_simplified", "小罠 丝巾魔术");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，看你的牌组最上面。可以把那张牌作为[[魔术箱]]在你的精灵区设置。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            CardIndex cardIndex = look();
            
            if(cardIndex != null)
            {
                if(playerChoiceActivate())
                {
                    putAsMagicBox(cardIndex);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
