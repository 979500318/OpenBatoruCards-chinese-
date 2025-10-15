package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_KodamaPhantomApparition extends Card {

    public SIGNI_G1_KodamaPhantomApparition()
    {
        setImageSets("WXK01-062");

        setOriginalName("幻怪　コダマ");
        setAltNames("ゲンカイコダマ Genkai Kodama");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。それが＜怪異＞のシグニの場合、それをエナゾーンに置く。" +
                "~#：【エナチャージ１】"
        );

        setName("en", "Kodama, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a <<Apparition>> SIGNI, put it into the ener zone." +
                "~#[[Ener Charge 1]]"
        );

		setName("zh_simplified", "幻怪 木灵");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。其是<<怪異>>精灵的场合，将其放置到能量区。" +
                "~#[[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
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
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.APPARITION) ||
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
