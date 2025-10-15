package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_NezumikoSmallTrap extends Card {

    public SIGNI_K1_NezumikoSmallTrap()
    {
        setImageSets("WXK01-109");

        setOriginalName("小罠　ネズミコ");
        setAltNames("ショウビンネズミコ Shoubin Nezumiko");
        setDescription("jp",
                "@E：あなたのトラッシュからレベル２以下の＜トリック＞のシグニを１枚まで対象とし、それをデッキの一番上に置く。"
        );

        setName("en", "Nezumiko, Small Trap");
        setDescription("en",
                "@E: Target up to 1 level 2 or lower <<Trick>> SIGNI from your trash, and put it on the top of your deck."
        );

		setName("zh_simplified", "小罠 黑鼠小僧");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把等级2以下的<<トリック>>精灵1张最多作为对象，将其放置到牌组最上面。\n"
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
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.TOP).own().SIGNI().withLevel(0,2).withClass(CardSIGNIClass.TRICK).fromTrash()).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
