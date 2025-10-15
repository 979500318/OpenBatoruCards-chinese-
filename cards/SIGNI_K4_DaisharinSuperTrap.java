package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K4_DaisharinSuperTrap extends Card {

    public SIGNI_K4_DaisharinSuperTrap()
    {
        setImageSets("WDK04-012");

        setOriginalName("超罠　ダイシャリン");
        setAltNames("チョウビンダイシャリン Choubin Daisharin");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。それがレベルが偶数のシグニの場合、それをエナゾーンに置く。"
        );

        setName("en", "Daisharin, Super Trap");
        setDescription("en",
                "@E: Reveal the top card of your deck. If it is a SIGNI with an even level, put it into the ener zone."
        );

		setName("zh_simplified", "超罠 死亡轮");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。其是等级在偶数的精灵的场合，将其放置到能量区。\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(4);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || cardIndex.getIndexedInstance().getLevelByRef() % 2 != 0 ||
               !putInEner(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
