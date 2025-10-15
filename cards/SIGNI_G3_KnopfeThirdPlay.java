package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G3_KnopfeThirdPlay extends Card {

    public SIGNI_G3_KnopfeThirdPlay()
    {
        setImageSets("WX25-P2-096");

        setOriginalName("参ノ遊　クネプフェ");
        setAltNames("サンノユウクネプフェ San no Yuu Kunopufe Knopfe");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中からレベル２以下のシグニ１枚をエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Knöpfe, Third Play");
        setDescription("en",
                "@E: Look at the top 3 cards of your deck. Put 1 level 2 or lower SIGNI from among them into the ener zone, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "叁之游 旋转盘");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把等级2以下的精灵1张放置到能量区，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().SIGNI().withLevel(0,2).fromLooked()).get();
            putInEner(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
