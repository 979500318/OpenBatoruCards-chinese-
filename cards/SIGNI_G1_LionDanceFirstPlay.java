package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_G1_LionDanceFirstPlay extends Card {

    public SIGNI_G1_LionDanceFirstPlay()
    {
        setImageSets("WX25-P2-091");

        setOriginalName("壱ノ遊　シシマイ");
        setAltNames("イチノユウ Ichi no Yuu Shishimai");
        setDescription("jp",
                "@E @[手札から＜遊具＞のシグニを１枚捨てる]@：あなたのデッキの上からカードを５枚見る。その中から＜遊具＞のシグニ１枚をエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Lion Dance, First Play");
        setDescription("en",
                "@E @[Discard 1 <<Playground Equipment>> SIGNI from your hand]@: Look at the top 5 cards of your deck. Put 1 <<Playground Equipment>> SIGNI from among them into the ener zone, and put the rest on the bottom of your deck in any order." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "壹之游 狮子舞");
        setDescription("zh_simplified", 
                "@E 从手牌把<<遊具>>精灵1张舍弃:从你的牌组上面看5张牌。从中把<<遊具>>精灵1张放置到能量区，剩下的任意顺序放置到牌组最下面。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromLooked()).get();
            putInEner(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
