package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_KakuzukeSmallTrap extends Card {

    public SIGNI_K1_KakuzukeSmallTrap()
    {
        setImageSets("WXK01-068");

        setOriginalName("小罠　カクヅケ");
        setAltNames("ショウビンカクヅケ Shoubin Kakuduke");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。それが＜トリック＞のシグニの場合、それをエナゾーンに置く。" +
                "~#：【エナチャージ１】"
        );

        setName("en", "Kakuzuke, Small Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a <<Trick>> SIGNI, put it into the ener zone." +
                "~#[[Ener Charge 1]]"
        );

		setName("zh_simplified", "小罠 等级鉴定");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。其是<<トリック>>精灵的场合，将其放置到能量区。" +
                "~#[[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(1000);

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
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null ||
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.TRICK) ||
               !putInEner(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
        }
    }
}
