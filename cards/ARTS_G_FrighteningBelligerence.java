package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_G_FrighteningBelligerence extends Card {

    public ARTS_G_FrighteningBelligerence()
    {
        setImageSets("WX24-P1-027");

        setOriginalName("剣戟森森");
        setAltNames("デュエルソード Dueru Soodo Duel Sword");
        setDescription("jp",
                "対戦相手のパワー12000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Frightening Belligerence");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 12000 or more, and banish it."
        );

		setName("zh_simplified", "剑戟森森");
        setDescription("zh_simplified", 
                "对战对手的力量12000以上的精灵1只作为对象，将其破坏。\n"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();
            banish(target);
        }
    }
}

