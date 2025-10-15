package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W1_BootsknifeSmallGun extends Card {

    public SIGNI_W1_BootsknifeSmallGun()
    {
        setImageSets("WX25-P1-066");

        setOriginalName("小装　ブーツナイフ");
        setAltNames("ショウソウブーツナイフ Shousou Buutsunaifu");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：あなたのデッキの上からカードを５枚見る。その中から＜ウェポン＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Bootsknife, Small Gun");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Look at the top 5 cards of your deck. Reveal 1 <<Weapon>> SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "小装 靴刃");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:从你的牌组上面看5张牌。从中把<<ウェポン>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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

            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.WEAPON).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
