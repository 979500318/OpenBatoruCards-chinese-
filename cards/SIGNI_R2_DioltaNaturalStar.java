package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_DioltaNaturalStar extends Card {

    public SIGNI_R2_DioltaNaturalStar()
    {
        setImageSets("WXDi-P07-064");

        setOriginalName("羅星　ジオールタ");
        setAltNames("ラセイジオールタ Rasei Jiooruta");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚公開し、それらのカードを好きな順番でデッキの一番下に置く。その後、この方法で公開されたすべてのカードがレベル１のシグニの場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Altar, Natural Planet");
        setDescription("en",
                "@E: Reveal the top three cards of your deck and put them on the bottom of your deck in any order. Then, if all cards revealed this way are level one SIGNI, vanish target SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Diolta, Natural Star");
        setDescription("en_fan",
                "@E: Reveal the top 3 cards of your deck, and put them on the bottom of your deck in any order. Then, if all cards revealed this way were level 1 SIGNI, target 1 of your opponent's SIGNI with power 3000 or less, and banish it."
        );

		setName("zh_simplified", "罗星 屈光度");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌公开，将这些牌任意顺序放置到牌组最下面。然后，这个方法公开的全部的牌都是等级1的精灵的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);

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
            if(reveal(3) == 0) return;
            
            boolean matchLevels = true;
            while(getRevealedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromRevealed()).get();
                matchLevels = matchLevels && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getLevelByRef() == 1;
                
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(matchLevels)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
                banish(target);
            }
        }
    }
}
