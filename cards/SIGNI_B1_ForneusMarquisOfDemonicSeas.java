package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_ForneusMarquisOfDemonicSeas extends Card {

    public SIGNI_B1_ForneusMarquisOfDemonicSeas()
    {
        setImageSets("WXK01-056");

        setOriginalName("魔海の侯爵　フォルネウス");
        setAltNames("マカイノコウシャクフォルネウス Makai no Koushaku Foruneusu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。それが＜悪魔＞のシグニの場合、それをエナゾーンに置く。" +
                "~#：【エナチャージ１】"
        );

        setName("en", "Forneus, Marquis of Demonic Seas");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a <<Devil>> SIGNI, put it into the ener zone." +
                "~#[[Ener Charge 1]]"
        );

		setName("zh_simplified", "魔海的侯爵 弗内乌斯 ");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。其是<<悪魔>>精灵的场合，将其放置到能量区。" +
                "~#[[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
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
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DEVIL) ||
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
