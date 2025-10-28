package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_W_BubbleWrap extends Card {

    public ARTS_W_BubbleWrap()
    {
        setImageSets("WX25-P1-036");

        setOriginalName("バブル・ラップ");
        setAltNames("バブルラップ Baburu Rappu");
        setDescription("jp",
                "対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Bubble Wrap");
        setDescription("en",
                "Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand."
        );

        setName("es", "Plástico de Burbujas");
        setDescription("es",
                "Selecciona 1 SIGNI oponente de nivel 2 o menor y devuelvela a la mano."
        );

        setName("zh_simplified", "泡影·韵律");
        setDescription("zh_simplified", 
                "对战对手的等级2以下的精灵1只作为对象，将其返回手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(target);
        }
    }
}

