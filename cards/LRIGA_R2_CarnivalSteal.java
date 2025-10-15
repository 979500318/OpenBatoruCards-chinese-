package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_R2_CarnivalSteal extends Card {

    public LRIGA_R2_CarnivalSteal()
    {
        setImageSets("WXDi-P11-033");

        setOriginalName("カーニバル　－奪－");
        setAltNames("カーニバルダツ Kaanibaru Datsu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。その後、この方法で公開したシグニと同じレベルの対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Carnival -Thievery-");
        setDescription("en",
                "@E: Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order. Then, vanish target SIGNI on your opponent's field with the same level as the SIGNI revealed this way."
        );
        
        setName("en_fan", "Carnival -Steal-");
        setDescription("en_fan",
                "@E: Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order. Then, target 1 of your opponent's SIGNI with the same level as the revealed this way SIGNI, and banish it."
        );

		setName("zh_simplified", "嘉年华 -夺-");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。然后，与这个方法公开的精灵相同等级的对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            int level = -1;
            if(cardIndex != null)
            {
                reveal(cardIndex);
                level = cardIndex.getIndexedInstance().getLevelByRef();
                addToHand(cardIndex);
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(level != -1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(level)).get();
                banish(target);
            }
        }
    }
}
