package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_W1_JapaneseGeckoPhantomDragon extends Card {

    public SIGNI_W1_JapaneseGeckoPhantomDragon()
    {
        setImageSets("WX24-P2-061");

        setOriginalName("幻竜　ニホンヤモリ");
        setAltNames("ゲンリュウニホンヤモリ Genryuu Nihonyamori");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手が%Xを支払わないかぎり、あなたのデッキの上からカードを３枚見る。その中から＜龍獣＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Japanese Gecko, Phantom Dragon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, unless your opponent pays %X, look at the top 3 cards of your deck. Reveal 1 <<Dragon Beast>> SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "幻龙 多疣壁虎");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，如果对战对手不把%X:支付，那么从你的牌组上面看3张牌。从中把<<龍獣>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(!payEner(getOpponent(), Cost.colorless(1)))
            {
                look(3);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST).fromLooked()).get();
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
}
