package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_AntlioSmallTrap extends Card {

    public SIGNI_K1_AntlioSmallTrap()
    {
        setImageSets("WXK01-108");

        setOriginalName("小罠　アリジゴ");
        setAltNames("ショウビンアリジゴ Shoubin Arijigo");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Antlio, Small Trap");
        setDescription("en",
                "@E: Put the top 2 cards of your deck into the trash."
        );

		setName("zh_simplified", "小罠 蚁狮漏斗");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(2000);

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
            millDeck(2);
        }
    }
}
