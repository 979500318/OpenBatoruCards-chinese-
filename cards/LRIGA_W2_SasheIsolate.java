package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_SasheIsolate extends Card {

    public LRIGA_W2_SasheIsolate()
    {
        setImageSets("WXDi-P13-030");

        setOriginalName("サシェ・アイソレート");
        setAltNames("サシェアイソレート Sashe Aisoreeto");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E %X %X：あなたのデッキの上からカードを５枚見る。その中からシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Sashe Isolate");
        setDescription("en",
                "@E: Return target SIGNI on your opponent's field to its owner's hand.\n@E %X %X: Look at the top five cards of your deck. Reveal a SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Sashe Isolate");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@E %X %X: Look at the top 5 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "莎榭·分离舞");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@E %X %X:从你的牌组上面看5张牌。从中把精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }

        private void onEnterEff2()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
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
