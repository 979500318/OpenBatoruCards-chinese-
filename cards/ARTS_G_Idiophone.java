package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_G_Idiophone extends Card {

    public ARTS_G_Idiophone()
    {
        setImageSets("WX25-P1-042");

        setOriginalName("体鳴楽器");
        setAltNames("フルートビート Furuuto Biito Flute Beat");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "Idiophone");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and put it into the ener zone."
        );

		setName("zh_simplified", "体鸣乐器");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，将其放置到能量区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,10000)).get();
            putInEner(target);
        }
    }
}

