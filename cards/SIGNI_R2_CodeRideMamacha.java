package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_CodeRideMamacha extends Card {

    public SIGNI_R2_CodeRideMamacha()
    {
        setImageSets("WXK01-077");

        setOriginalName("コードライド　ママチャ");
        setAltNames("コードライドママチャ Koodo Raido Mamacha");
        setDescription("jp",
                "@U：このシグニがドライブ状態になったとき、あなたのデッキの一番上を公開する。それが＜乗機＞のシグニの場合、それを手札に加える。"
        );

        setName("en", "Code Ride Mamacha");
        setDescription("en",
                "@U: When this SIGNI enters the drive state, reveal the top card of your deck. If it is a <<Riding Machine>> SIGNI, add it to your hand."
        );

		setName("zh_simplified", "骑乘代号 菜篮自行车");
        setDescription("zh_simplified", 
                "@U :当这只精灵变为驾驶状态时，你的牌组最上面公开。其是<<乗機>>精灵的场合，将其加入手牌。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.DRIVE, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) ||
               !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.RIDING_MACHINE) || !addToHand(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
